package com.blood.presure.Fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.SurfaceTexture;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.exoplayer.DefaultRenderersFactory;
import androidx.vectordrawable.graphics.drawable.PathInterpolatorCompat;

import com.blood.presure.Interface.MeasuringCallbacks;
import com.blood.presure.Measurement.DataCenter;
import com.blood.presure.Model.NewRecordModel;
import com.blood.presure.Model.NewSoundManager;
import com.blood.presure.R;
import com.blood.presure.Utils.NewSaveLanguageUtils;
import com.blood.presure.Utils.NewUscreen;
import com.blood.presure.activity.NewHeartRateActivityNew;
import com.blood.presure.activity.NewResultActivity;
import com.blood.presure.activity.NewResultHeartActivity;
import com.blood.presure.anaylzer.OutputAnalyzerNew;
import com.blood.presure.chart.NewCameraService;
import com.blood.presure.chart.NewChartView;
import com.blood.presure.chart.NewcirclesView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.messaging.ServiceStarter;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@UnstableApi
public class NewMeasureFragment2 extends Fragment {
    private static int RESULT_CAMERA = 123;
    boolean _sound = true;
    boolean _vibrate = true;

    public TextView average;

    public ImageView beat;

    public TextView bpm;
    private NewCameraService newCameraService;
    public TextureView cameraTextureView;
    private NewcirclesView circles2;

    public TextView date;

    public TextView gender;
    public NewChartView graphTextureView;
    boolean haI = true;
    private ImageView imgStart;

    public View indicator;
    boolean isShowing = false;

    public boolean justShared = false;
    private Handler mainHandler = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message message) {
            super.handleMessage(message);
            if (message.what == 1) {
                ((TextView) NewMeasureFragment2.this.parent.findViewById(R.id.textView4)).setText(message.obj.toString());
                NewMeasureFragment2.this.bpm.setText(message.obj.toString());
            }
            if (message.what == 2) {
                NewMeasureFragment2.this.outputAnalyzerNew.stop();
                if (NewHeartRateActivityNew.getInstance() != null) {
                    Intent intent = new Intent(NewHeartRateActivityNew.getInstance(), NewResultHeartActivity.class);
                    intent.putExtra("name_result", message.obj.toString());
                    NewHeartRateActivityNew.getInstance().startActivity(intent);
                }
            }
            if (message.what == 3) {
                NewMeasureFragment2.this.outputAnalyzerNew.stop();
            }
        }
    };
    public TextView max;
    MeasuringCallbacks measureCallback = new MeasuringCallbacks() {
        public void vallyDetected(int i) {
            TextView access$000 = NewMeasureFragment2.this.bpm;
            access$000.setText(i + "");
            NewMeasureFragment2.this.beatOnce();
        }

        public void measuringFinished(int i) {
            TextView access$000 = NewMeasureFragment2.this.bpm;
            access$000.setText(i + "");
            measuringStoped();
            NewMeasureFragment2.this.showStateDialog(i);
        }

        public void measuringStoped() {
            if (NewMeasureFragment2.this.seekbarTimer != null) {
                NewMeasureFragment2.this.seekbarTimer.cancel();
                CountDownTimer unused = NewMeasureFragment2.this.seekbarTimer = null;
            }
        }

        public void fingerNotDetected() {
            NewMeasureFragment2.this.showDialogTuto();
        }

        public void measuringStarted() {
            NewMeasureFragment2.this.startSeekbarAnimation();
        }

        public void fingerCantBeDetected() {
            NewHeartRateActivityNew.getInstance().analyzer.restart();
        }
    };
    private View measuringHolder;
    public TextView min;
    public OutputAnalyzerNew outputAnalyzerNew;

    public View parent;
    public TextView progressTv;

    public TextView result;
    public CountDownTimer seekbarTimer;
    public ImageView sound;
    boolean startAfterPermission = false;

    public TextView state;
    private View tapToMeasureHolder;
    private TextView tvExport;
    private TextView tvStart;
    private Vibrator f10187v;
    public ImageView vibrate;

    public void startNoFingerProcess() {
    }

    public void startSeekbarAnimation() {
        this.progressTv.setText("measuring ...     ");
        this.seekbarTimer = new CountDownTimer(50000, 100) {
            public void onFinish() {
            }

            public void onTick(long j) {
                if (NewHeartRateActivityNew.getInstance() != null && NewHeartRateActivityNew.getInstance().analyzer != null) {
                    TextView textView = NewMeasureFragment2.this.progressTv;
                    textView.setText(" Measuring... " + ((int) (((((float) NewHeartRateActivityNew.getInstance().analyzer.tickPassedWhileScaning) * 45.0f) / 9000.0f) * 100.0f)) + "%");
                }
            }
        }.start();
    }

    public void scanFinished(int i, int i2) {
        if (i != 0) {
            NewRecordModel newRecordModel = new NewRecordModel(i, System.currentTimeMillis());
            newRecordModel.state = i2;
            String json = new Gson().toJson((Object) newRecordModel);
            Intent intent = new Intent(getActivity(), NewResultActivity.class);
            intent.putExtra("record", json);
            startActivityForResult(intent, 1);
            return;
        }
        showDialogTuto();
    }

    public void showStateDialog(final int i) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.BottomSheetDialog);
        bottomSheetDialog.setContentView((int) R.layout.state_dialog);
        bottomSheetDialog.show();
        bottomSheetDialog.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.findViewById(R.id.normal).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
                NewMeasureFragment2.this.scanFinished(i, 0);
            }
        });
        bottomSheetDialog.findViewById(R.id.active).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
                NewMeasureFragment2.this.scanFinished(i, 1);
            }
        });
    }

    public void mainHeartAnimation() {
        if (!this.haI) {
            this.imgStart.animate().scaleY(1.0f).scaleX(1.0f).setDuration(500).setStartDelay(250).setInterpolator(new LinearInterpolator()).setListener(new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    NewMeasureFragment2 newMeasureFragment2 = NewMeasureFragment2.this;
                    newMeasureFragment2.haI = !newMeasureFragment2.haI;
                    NewMeasureFragment2.this.mainHeartAnimation();
                }
            }).start();
        } else {
            this.imgStart.animate().scaleY(0.8f).scaleX(0.8f).setDuration(1100).setStartDelay(0).setInterpolator(new BounceInterpolator()).setListener(new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    NewMeasureFragment2 newMeasureFragment2 = NewMeasureFragment2.this;
                    newMeasureFragment2.haI = !newMeasureFragment2.haI;
                    NewMeasureFragment2.this.mainHeartAnimation();
                }
            }).start();
        }
    }

    public static NewMeasureFragment2 newInstance() {
        return new NewMeasureFragment2();
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.fragment_measure, viewGroup, false);
    }

    @SuppressLint("WrongConstant")
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.parent = view;
        this.f10187v = (Vibrator) getActivity().getSystemService("vibrator");
        this.beat = (ImageView) this.parent.findViewById(R.id.beat);
        NewcirclesView circlesview = (NewcirclesView) this.parent.findViewById(R.id.circles2);
        this.circles2 = circlesview;
        circlesview.autoSpawn = false;
        this.cameraTextureView = (TextureView) this.parent.findViewById(R.id.textureView2);
        this.graphTextureView = (NewChartView) this.parent.findViewById(R.id.graphTextureView);
        this.vibrate = (ImageView) this.parent.findViewById(R.id.vibrate);
        this.sound = (ImageView) this.parent.findViewById(R.id.sound);
        this.progressTv = (TextView) this.parent.findViewById(R.id.progress);
        this.average = (TextView) this.parent.findViewById(R.id.average);
        this.max = (TextView) this.parent.findViewById(R.id.max);
        this.min = (TextView) this.parent.findViewById(R.id.min);
        this.tvStart = (TextView) this.parent.findViewById(R.id.textView);
        this.tvExport = (TextView) this.parent.findViewById(R.id.tv_export);
        if (NewSaveLanguageUtils.LoadPref("vibrate", getActivity()) == 0) {
            this.vibrate.setAlpha(1.0f);
        } else {
            this.vibrate.setAlpha(0.5f);
            this._vibrate = false;
        }
        if (NewSaveLanguageUtils.LoadPref("sound", getActivity()) == 0) {
            this.sound.setAlpha(1.0f);
        } else {
            this.sound.setAlpha(0.5f);
            this._sound = false;
        }
        if (DataCenter.getRecords() != null) {
            DataCenter.getRecords();
        }
        this.vibrate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (NewSaveLanguageUtils.LoadPref("vibrate", NewMeasureFragment2.this.getActivity()) == 0) {
                    NewMeasureFragment2.this._vibrate = false;
                    NewMeasureFragment2.this.vibrate.setAlpha(0.5f);
                    NewSaveLanguageUtils.saveLanguage("vibrate", "1", NewMeasureFragment2.this.getActivity());
                    return;
                }
                NewMeasureFragment2.this._vibrate = true;
                NewMeasureFragment2.this.vibrate.setAlpha(1.0f);
                NewSaveLanguageUtils.saveLanguage("vibrate", "0.5", NewMeasureFragment2.this.getActivity());
            }
        });
        this.sound.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (NewSaveLanguageUtils.LoadPref("sound", NewMeasureFragment2.this.getActivity()) == 0) {
                    NewMeasureFragment2.this.sound.setAlpha(0.5f);
                    NewMeasureFragment2.this._sound = false;
                    NewSaveLanguageUtils.saveLanguage("sound", "1", NewMeasureFragment2.this.getActivity());
                    return;
                }
                NewMeasureFragment2.this._sound = true;
                NewMeasureFragment2.this.sound.setAlpha(1.0f);
                NewSaveLanguageUtils.saveLanguage("sound", "0.5", NewMeasureFragment2.this.getActivity());
            }
        });
        this.tapToMeasureHolder = this.parent.findViewById(R.id.tapToMeasureHolder);
        this.measuringHolder = this.parent.findViewById(R.id.measuringHolder);
        this.bpm = (TextView) this.parent.findViewById(R.id.currentbpm);
        this.tapToMeasureHolder.setVisibility(0);
        this.measuringHolder.setVisibility(8);
        ImageView imageView2 = (ImageView) this.parent.findViewById(R.id.start);
        this.imgStart = imageView2;
        imageView2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (NewMeasureFragment2.this.checkPermission()) {
//                    if (!FastSave.getInstance().getBoolean(NewEUGeneralHelper.GOOGLE_PLAY_STORE_USER_KEY, false)) {
//
//                        AdAdmob adAdmob = new AdAdmob( getActivity());
//                        adAdmob.FullscreenAd_Counter(getActivity());
//                    }
                    NewMeasureFragment2.this.startMeasuring();
                    return;
                }
                NewMeasureFragment2.this.startAfterPermission = true;
                NewMeasureFragment2.this.askPermission();
            }
        });
        this.parent.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (NewHeartRateActivityNew.getInstance() != null) {
                    NewHeartRateActivityNew.getInstance().stopMeasuring();
                }
                NewMeasureFragment2.this.measuringStoped();
                NewMeasureFragment2.this.funcStopMess();
            }
        });
        this.tvStart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (NewMeasureFragment2.this.checkPermission()) {
                    NewMeasureFragment2.this.startMeasuring();
                    return;
                }
                NewMeasureFragment2.this.startAfterPermission = true;
                NewMeasureFragment2.this.askPermission();
            }
        });
        this.parent.findViewById(R.id.tutorial).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                NewMeasureFragment2.this.showDialogTuto();
            }
        });
        mainHeartAnimation();
        updateData();
        this.tvExport.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                NewMeasureFragment2 newMeasureFragment2 = NewMeasureFragment2.this;
                Intent access$600 = newMeasureFragment2.getTextIntent((String) ((TextView) newMeasureFragment2.parent.findViewById(R.id.textView4)).getText());
                boolean unused = NewMeasureFragment2.this.justShared = true;
                NewMeasureFragment2 newMeasureFragment22 = NewMeasureFragment2.this;
                newMeasureFragment22.startActivity(Intent.createChooser(access$600, newMeasureFragment22.getString(R.string.send_output_to)));
            }
        });
    }


    public Intent getTextIntent(String str) {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("text/plain");
        intent.putExtra("android.intent.extra.SUBJECT", String.format(getString(R.string.output_header_template), new Object[]{new SimpleDateFormat(getString(R.string.dateFormat), Locale.getDefault()).format(new Date())}));
        intent.putExtra("android.intent.extra.TEXT", str);
        return intent;
    }


    public void initHeart(View view) {
        this.newCameraService = new NewCameraService(getActivity(), this.mainHandler);
        TextureView textureView = (TextureView) view.findViewById(R.id.textureView2);
        SurfaceTexture surfaceTexture = textureView.getSurfaceTexture();
        if (surfaceTexture != null && !this.justShared) {
            Surface surface = new Surface(surfaceTexture);
            Log.e("TAG", "initHeart: "+surfaceTexture );

            this.outputAnalyzerNew = new OutputAnalyzerNew(getActivity(), (TextureView) view.findViewById(R.id.tv_graph), this.mainHandler);
            this.newCameraService.start(surface);
            this.outputAnalyzerNew.measurePulse(textureView, this.newCameraService);
        }
    }

    public void askPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), "android.permission.CAMERA") != 0 && Build.VERSION.SDK_INT >= 23) {
            requestPermissions(new String[]{"android.permission.CAMERA"}, RESULT_CAMERA);
        }
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == RESULT_CAMERA && iArr.length > 0 && strArr[0].equals("android.permission.CAMERA") && iArr[0] == 0) {
            startMeasuring();
        }
    }

    public void updateData() {
        List<NewRecordModel> records = DataCenter.getRecords();
        if (records != null) {
            int i = 0;
            int i2 = 0;
            int i3 = ServiceStarter.ERROR_UNKNOWN;
            for (int i4 = 0; i4 < records.size(); i4++) {
                int i5 = records.get(i4).beat;
                if (i5 > i) {
                    i = i5;
                }
                if (i5 < i3) {
                    i3 = i5;
                }
                i2 += i5;
            }
            if (records.size() > 0) {
                int size = i2 / records.size();
                TextView textView = this.min;
                textView.setText(i3 + "");
                TextView textView2 = this.max;
                textView2.setText(i + "");
                TextView textView3 = this.average;
                textView3.setText(size + "");
            }
        }
    }

    public void vibrate(int i) {
        if (!this._vibrate) {
            return;
        }
        if (Build.VERSION.SDK_INT >= 26) {
            this.f10187v.vibrate(VibrationEffect.createOneShot((long) i, -1));
        } else {
            this.f10187v.vibrate((long) i);
        }
    }

    public void sound() {
        if (this._sound) {
            NewSoundManager.getInstance().play();
        }
    }

    @SuppressLint("RestrictedApi")
    public void beatOnce() {
        vibrate(50);
        this.circles2.spawn(600, PathInterpolatorCompat.MAX_NUM_POINTS);
        sound();
        this.beat.clearAnimation();
        this.beat.animate().scaleX(1.35f).scaleY(1.35f).setDuration(150).setListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                NewMeasureFragment2.this.beat.clearAnimation();
                NewMeasureFragment2.this.beat.animate().scaleX(1.0f).scaleY(1.0f).setDuration(300).setListener((Animator.AnimatorListener) null).start();
            }
        }).start();
    }

    public void onPause() {
        super.onPause();
        measuringStoped();
        if (NewHeartRateActivityNew.getInstance() != null) {
            NewHeartRateActivityNew.getInstance().stopMeasuring();
        }
        funcStopMess();
    }


    public void funcStopMess() {
        NewCameraService newCameraService2 = this.newCameraService;
        if (newCameraService2 != null) {
            newCameraService2.stop();
        }
        OutputAnalyzerNew outputAnalyzerNew2 = this.outputAnalyzerNew;
        if (outputAnalyzerNew2 != null) {
            outputAnalyzerNew2.stop();
        }
        this.outputAnalyzerNew = new OutputAnalyzerNew(getActivity(), (TextureView) this.parent.findViewById(R.id.tv_graph), this.mainHandler);
    }

    public void showDialogTuto() {
        this.isShowing = true;
        measuringStoped();
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.tuto_dialog);
        dialog.show();
        NewUscreen.Init(getActivity());
        dialog.findViewById(R.id.bg).getLayoutParams().width = (int) (((float) NewUscreen.width) * 0.95f);
        dialog.findViewById(R.id.bg).getLayoutParams().height = (int) (((float) NewUscreen.height) * 0.95f);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            public void onDismiss(DialogInterface dialogInterface) {
                NewMeasureFragment2.this.isShowing = false;
            }
        });
        dialog.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
                NewMeasureFragment2.this.isShowing = false;
            }
        });
        tutoAnimation((ImageView) dialog.findViewById(R.id.hand), dialog.findViewById(R.id.animationHolder));
    }

    public boolean checkPermission() {
        return ContextCompat.checkSelfPermission(getContext(), "android.permission.CAMERA") == 0;
    }

    public void onDestroy() {
        super.onDestroy();
        NewSoundManager.getInstance().destroy();
    }

    public void tutoAnimation(final ImageView imageView, final View view) {
        imageView.setX((float) NewUscreen.width);
        imageView.setY((float) NewUscreen.width);
        imageView.animate().y((float) (NewUscreen.width / 15)).x((float) (NewUscreen.width / 6)).setDuration(1500).setStartDelay(1000).setListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
            }
        }).start();
        view.animate().alpha(0.0f).setDuration(1000).setStartDelay(DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS).setListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                if (NewMeasureFragment2.this.isShowing) {
                    imageView.setX((float) NewUscreen.width);
                    imageView.setY((float) NewUscreen.width);
                    view.clearAnimation();
                    view.animate().alpha(1.0f).setDuration(1000).setStartDelay(1000).setListener(new AnimatorListenerAdapter() {
                        public void onAnimationEnd(Animator animator) {
                            super.onAnimationEnd(animator);
                            if (NewMeasureFragment2.this.isShowing) {
                                NewMeasureFragment2.this.tutoAnimation(imageView, view);
                            }
                        }
                    }).start();
                }
            }
        }).start();
    }

    @SuppressLint("WrongConstant")
    public void startMeasuring() {
        this.progressTv.setText(getString(R.string.press_finger));
        this.measuringHolder.setVisibility(0);
        this.tapToMeasureHolder.setVisibility(8);
        this.bpm.setText("00");
        new CountDownTimer(320, 300) {
            public void onTick(long j) {
            }

            public void onFinish() {
                if (NewHeartRateActivityNew.getInstance() != null) {
                    NewHeartRateActivityNew.getInstance().startMeasuring(NewMeasureFragment2.this.cameraTextureView, NewMeasureFragment2.this.graphTextureView, NewMeasureFragment2.this.measureCallback);
                }
                NewMeasureFragment2 newMeasureFragment2 = NewMeasureFragment2.this;
                newMeasureFragment2.initHeart(newMeasureFragment2.parent);
            }
        }.start();
    }

    @SuppressLint("WrongConstant")
    public void measuringStoped() {
        CountDownTimer countDownTimer = this.seekbarTimer;
        if (countDownTimer != null) {
            countDownTimer.cancel();
            this.seekbarTimer = null;
        }
        this.measuringHolder.setVisibility(8);
        this.tapToMeasureHolder.setVisibility(0);
        updateData();
    }
}

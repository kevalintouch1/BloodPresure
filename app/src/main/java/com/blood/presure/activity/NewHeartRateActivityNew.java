package com.blood.presure.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.blood.presure.Fragment.NewChartFragment;
import com.blood.presure.Fragment.NewInfoFragment;
import com.blood.presure.Fragment.NewMeasureFragment2;
import com.blood.presure.Fragment.NewSettingsFragment;
import com.blood.presure.Fragment.NewTrackerFragment;
import com.blood.presure.Interface.MeasuringCallbacks;
import com.blood.presure.R;
import com.blood.presure.Utils.NewSettingUtils;
import com.blood.presure.Utils.NewUscreen;
import com.blood.presure.ads.AdmobAdsHelper;
import com.blood.presure.anaylzer.OutputAnalyzer;
import com.blood.presure.chart.NewCameraService;
import com.blood.presure.chart.NewChartView;

import java.util.ArrayList;

public class NewHeartRateActivityNew extends NewBaseActivity {
    public static final int MESSAGE_CAMERA_NOT_AVAILABLE = 3;
    public static final int MESSAGE_UPDATE_FINAL = 2;
    public static final int MESSAGE_UPDATE_REALTIME = 1;
    static NewHeartRateActivityNew instance;
    String TAG;
    public OutputAnalyzer analyzer;
    MeasuringCallbacks callback;
    private final NewCameraService newCameraService;
    TextureView cameraTextureView;
    View chartV;
    View infoV;
    boolean interIsLoading;
    private final Handler mainHandler;
    View measureV;
    boolean measuring = false;
    View settings;
    View trackerV;

    public enum VIEW_STATE {
        MEASUREMENT, SHOW_RESULTS
    }

    public NewHeartRateActivityNew() {
        mainHandler = new Handler(Looper.getMainLooper()) {
            public void handleMessage(Message message) {
                super.handleMessage(message);
                if (message.what == 1) {
                    if (NewHeartRateActivityNew.this.callback != null) {
                        NewHeartRateActivityNew.this.callback.vallyDetected(((Integer) message.obj).intValue());
                    }
                } else if (message.what == 2) {
                    if (NewHeartRateActivityNew.this.callback != null) {
                        NewHeartRateActivityNew.this.callback.measuringFinished(((Integer) message.obj).intValue());
                    }
                    NewHeartRateActivityNew.this.measuring = false;
                } else if (message.what == 3) {
                    NewHeartRateActivityNew.this.analyzer.stop();
                    NewHeartRateActivityNew.this.measuring = false;
                    if (NewHeartRateActivityNew.this.callback != null) {
                        NewHeartRateActivityNew.this.callback.measuringStoped();
                    }
                } else if (message.what == 4) {
                    if (NewHeartRateActivityNew.this.callback != null) {
                        NewHeartRateActivityNew.this.callback.fingerNotDetected();
                    }
                    NewHeartRateActivityNew.this.stopMeasuring();
                } else if (message.what == 5) {
                    if (NewHeartRateActivityNew.this.callback != null) {
                        NewHeartRateActivityNew.this.callback.measuringStarted();
                    }
                } else if (message.what == 6 && NewHeartRateActivityNew.this.callback != null) {
                    NewHeartRateActivityNew.this.callback.fingerCantBeDetected();
                }
            }
        };

        this.newCameraService = new NewCameraService(NewHeartRateActivityNew.this, this.mainHandler);
        this.TAG = "splash";
        this.interIsLoading = false;
    }

    public void startMeasuring(TextureView textureView, NewChartView newChartView, MeasuringCallbacks measuringCallbacks) {
        this.analyzer = new OutputAnalyzer(this, newChartView, this.mainHandler, null);
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < 70; i++) {
            arrayList.add(Float.valueOf(0.0f));
        }
        newChartView.data = arrayList;
        this.callback = measuringCallbacks;
        this.cameraTextureView = textureView;
    }

    public void stopMeasuring() {
        if (this.measuring) {
            this.newCameraService.stop();
            try {
                OutputAnalyzer outputAnalyzer = this.analyzer;
                if (outputAnalyzer != null) {
                    outputAnalyzer.stop();
                }
                this.analyzer = new OutputAnalyzer(this, findViewById(R.id.graphTextureView), this.mainHandler, null);
                this.measuring = false;
                MeasuringCallbacks measuringCallbacks = this.callback;
                if (measuringCallbacks != null) {
                    measuringCallbacks.measuringStoped();
                }
                this.callback = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static NewHeartRateActivityNew getInstance() {
        return instance;
    }

    public void onPause() {
        super.onPause();
        stopMeasuring();
    }

    public void onDestroy() {
        super.onDestroy();
        instance = null;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        instance = this;
        setContentView(R.layout.activity_heart_rate);
        ImageView imgBack = findViewById(R.id.img_back);
        this.measureV = findViewById(R.id.measureV);
        this.trackerV = findViewById(R.id.trackerV);
        this.infoV = findViewById(R.id.infoV);
        this.settings = findViewById(R.id.settings);
        this.chartV = findViewById(R.id.chartV);
        showMeasure();
        this.measureV.setOnClickListener(view -> NewHeartRateActivityNew.this.showMeasure());
        this.trackerV.setOnClickListener(view -> NewHeartRateActivityNew.this.showTracker(false));
        this.infoV.setOnClickListener(view -> NewHeartRateActivityNew.this.showKnowledge());
        this.settings.setOnClickListener(view -> NewHeartRateActivityNew.this.showSettings());
        this.chartV.setOnClickListener(view -> NewHeartRateActivityNew.this.showChart());
        imgBack.setOnClickListener(view -> onBackPressed());

        new AdmobAdsHelper(this).bannerAds(this,findViewById(R.id.adView));
        AdmobAdsHelper.LoadAdMobInterstitialAd(this);
    }


    private void removeAllFragments() {
        for (Fragment remove : getSupportFragmentManager().getFragments()) {
            getSupportFragmentManager().beginTransaction().remove(remove).commit();
        }
    }

    @SuppressLint("UnsafeOptInUsageError")
    public void showMeasure() {
        this.measureV.setAlpha(1.0f);
        this.trackerV.setAlpha(0.5f);
        this.infoV.setAlpha(0.5f);
        this.settings.setAlpha(0.5f);
        this.chartV.setAlpha(0.5f);
        removeAllFragments();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container_view, new NewMeasureFragment2()).commit();
    }

    public void showKnowledge() {
        this.measureV.setAlpha(0.5f);
        this.trackerV.setAlpha(0.5f);
        this.settings.setAlpha(0.5f);
        this.infoV.setAlpha(1.0f);
        this.chartV.setAlpha(0.5f);
        removeAllFragments();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container_view, new NewInfoFragment()).commit();
    }

    public void showTracker(boolean z) {
        this.measureV.setAlpha(0.5f);
        this.settings.setAlpha(0.5f);
        this.trackerV.setAlpha(1.0f);
        this.infoV.setAlpha(0.5f);
        this.chartV.setAlpha(0.5f);
        removeAllFragments();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container_view, new NewTrackerFragment()).commit();
    }

    public void showSettings() {
        this.measureV.setAlpha(0.5f);
        this.settings.setAlpha(1.0f);
        this.trackerV.setAlpha(0.5f);
        this.infoV.setAlpha(0.5f);
        this.chartV.setAlpha(0.5f);
        removeAllFragments();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container_view, new NewSettingsFragment()).commit();
    }

    public void showChart() {
        this.measureV.setAlpha(0.5f);
        this.settings.setAlpha(0.5f);
        this.trackerV.setAlpha(0.5f);
        this.infoV.setAlpha(0.5f);
        this.chartV.setAlpha(1.0f);
        removeAllFragments();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container_view, new NewChartFragment()).commit();
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == -1) {
            showTracker(true);
        }
    }

    @SuppressLint("ResourceType")
    public void showRateUs() {
        NewUscreen.Init(this);
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_rate_app);
        dialog.getWindow().setBackgroundDrawableResource(17170445);
        dialog.show();
        dialog.findViewById(R.id.bg).getLayoutParams().width = (int) (((double) NewUscreen.width) * 0.9d);
        final RatingBar ratingBar = dialog.findViewById(R.id.ratingBar);
        dialog.findViewById(R.id.rate).setEnabled(false);
        dialog.findViewById(R.id.rate).setAlpha(0.5f);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float f, boolean z) {
                if (f > 0.0f) {
                    dialog.findViewById(R.id.rate).setEnabled(true);
                    dialog.findViewById(R.id.rate).setAlpha(1.0f);
                    return;
                }
                dialog.findViewById(R.id.rate).setEnabled(false);
                dialog.findViewById(R.id.rate).setAlpha(0.5f);
            }
        });
        dialog.findViewById(R.id.rate).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (ratingBar.getRating() >= 4.0f) {
                    NewSettingUtils.showRatingGoogle(NewHeartRateActivityNew.this, new NewSettingUtils.onResultRate() {
                        public void onResult() {
                            Toast.makeText(NewHeartRateActivityNew.this, NewHeartRateActivityNew.this.getString(R.string.thanks_rating), 0).show();
                        }
                    });
                } else {
                    NewHeartRateActivityNew heartRateActivity = NewHeartRateActivityNew.this;
                    Toast.makeText(heartRateActivity, heartRateActivity.getString(R.string.thanks_rating), 0).show();
                }
                dialog.dismiss();
            }
        });
    }

    public void onBackPressed() {
        AdmobAdsHelper.ShowFullAds(this);
        finish();
    }
}

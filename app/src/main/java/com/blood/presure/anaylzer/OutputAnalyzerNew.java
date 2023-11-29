package com.blood.presure.anaylzer;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.TextureView;

import com.blood.presure.Measurement.MeasureStore;
import com.blood.presure.chart.NewCameraService;
import com.blood.presure.Measurement.Measurement;

import com.blood.presure.chart.NewChartDrawer;
import com.blood.presure.R;

import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Locale;
import java.util.concurrent.CopyOnWriteArrayList;

public class OutputAnalyzerNew {
    
    public final Activity activity;
    
    public final NewChartDrawer newChartDrawer;
    private final int clipLength = 3500;
    
    public int detectedValleys = 0;
    
    public final Handler mainHandler;
    private final int measurementInterval = 45;
    private final int measurementLength = 15000;
    
    public MeasureStore store;
    private int ticksPassed = 0;
    private CountDownTimer timer;
    
    public final CopyOnWriteArrayList<Long> valleys = new CopyOnWriteArrayList<>();

    static  int access$004(OutputAnalyzerNew outputAnalyzerNew) {
        int i = outputAnalyzerNew.ticksPassed + 1;
        outputAnalyzerNew.ticksPassed = i;
        return i;
    }

    public OutputAnalyzerNew(Activity activity2, TextureView textureView, Handler handler) {
        this.activity = activity2;
        this.newChartDrawer = new NewChartDrawer(textureView);
        this.mainHandler = handler;
    }

    
    public boolean detectValley() {
        CopyOnWriteArrayList<Measurement<Integer>> lastStdValues = this.store.getLastStdValues(13);
        if (lastStdValues.size() < 13) {
            return false;
        }
        Integer num = (Integer) lastStdValues.get((int) Math.ceil(6.5d)).measurement;
        Iterator<Measurement<Integer>> it = lastStdValues.iterator();
        while (it.hasNext()) {
            if (((Integer) it.next().measurement).intValue() < num.intValue()) {
                return false;
            }
        }
        return !((Integer) lastStdValues.get((int) Math.ceil(6.5d)).measurement).equals(lastStdValues.get(((int) Math.ceil(6.5d)) - 1).measurement);
    }

    public void measurePulse(TextureView textureView, NewCameraService newCameraService) {
        if (textureView != null) {
            this.store = new MeasureStore();
            Log.e("TAGasdasdsadasd", "measurePulse: "+ store);
            this.detectedValleys = 0;

             new CountDownTimer(15000, 45) {
                public void onTick(long j) {
                    Log.e("TAGasdasdsadasd", "onTick1: "+ j);
                    Log.e("TAGasdasdsadasd", "onTick2: "+ OutputAnalyzerNew.access$004(OutputAnalyzerNew.this) * 45);
                    if (3500 <= OutputAnalyzerNew.access$004(OutputAnalyzerNew.this) * 45) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                mo44030x9c36ed65(textureView,j);
                            }
                        }).start();
                    }
                }
                
                public  void mo44030x9c36ed65(TextureView textureView, long j) {
                    try {
                        Log.e("TAGasdasdsadasd", "mo44030x9c36ed65: "+ j);
                        float f;
                        float f2;
                        Bitmap bitmap = textureView.getBitmap();
                        int width = textureView.getWidth() * textureView.getHeight();
                        int[] iArr = new int[width];
                        bitmap.getPixels(iArr, 0, textureView.getWidth(), 0, 0, textureView.getWidth(), textureView.getHeight());
                        int i = 0;
                        for (int i2 = 0; i2 < width; i2++) {
                            i += (iArr[i2] >> 16) & 255;
                        }
                        OutputAnalyzerNew.this.store.add(i);
                        Log.e("TAGasdasdsadasd", "mo44030x9c36ed65: "+ detectValley());
                        if (OutputAnalyzerNew.this.detectValley()) {
                            OutputAnalyzerNew outputAnalyzerNew = OutputAnalyzerNew.this;
                            outputAnalyzerNew.detectedValleys = outputAnalyzerNew.detectedValleys + 1;
                            OutputAnalyzerNew.this.valleys.add(Long.valueOf(OutputAnalyzerNew.this.store.getLastTimestamp().getTime()));
                            Locale locale = Locale.getDefault();
                            String quantityString = OutputAnalyzerNew.this.activity.getResources().getQuantityString(R.plurals.measurement_output_template, OutputAnalyzerNew.this.detectedValleys);
                            Object[] objArr = new Object[3];
                            if (OutputAnalyzerNew.this.valleys.size() == 1) {
                                f2 = ((float) OutputAnalyzerNew.this.detectedValleys) * 60.0f;
                                f = Math.max(1.0f, ((float) ((15000 - j) - 3500)) / 1000.0f);
                            } else {
                                f2 = ((float) (OutputAnalyzerNew.this.detectedValleys - 1)) * 60.0f;
                                f = Math.max(1.0f, ((float) (((Long) OutputAnalyzerNew.this.valleys.get(OutputAnalyzerNew.this.valleys.size() - 1)).longValue() - ((Long) OutputAnalyzerNew.this.valleys.get(0)).longValue())) / 1000.0f);
                            }
                            objArr[0] = Float.valueOf(f2 / f);
                            objArr[1] = Integer.valueOf(OutputAnalyzerNew.this.detectedValleys);
                            objArr[2] = Float.valueOf((((float) ((15000 - j) - 3500)) * 1.0f) / 1000.0f);
                            OutputAnalyzerNew.this.sendMessage(1, String.format(locale, quantityString, objArr));
                        }
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                mo44029x19ec3886();
                            }
                        }).start();
                    } catch (Exception e) {

                    }
                }


                public  void mo44029x19ec3886() {
                    OutputAnalyzerNew.this.newChartDrawer.draw(OutputAnalyzerNew.this.store.getStdValues());
                }

                public void onFinish() {
                    CopyOnWriteArrayList<Measurement<Float>> stdValues = OutputAnalyzerNew.this.store.getStdValues();
                    if (OutputAnalyzerNew.this.valleys.size() == 0) {
                        OutputAnalyzerNew.this.mainHandler.sendMessage(Message.obtain(OutputAnalyzerNew.this.mainHandler, 3, "No valleys detected - there may be an issue when accessing the camera."));
                        return;
                    }
                    String format = String.format(Locale.getDefault(), OutputAnalyzerNew.this.activity.getResources().getQuantityString(R.plurals.measurement_output_template, OutputAnalyzerNew.this.detectedValleys - 1), new Object[]{Float.valueOf((((float) (OutputAnalyzerNew.this.detectedValleys - 1)) * 60.0f) / Math.max(1.0f, ((float) (((Long) OutputAnalyzerNew.this.valleys.get(OutputAnalyzerNew.this.valleys.size() - 1)).longValue() - ((Long) OutputAnalyzerNew.this.valleys.get(0)).longValue())) / 1000.0f)), Integer.valueOf(OutputAnalyzerNew.this.detectedValleys - 1), Float.valueOf((((float) (((Long) OutputAnalyzerNew.this.valleys.get(OutputAnalyzerNew.this.valleys.size() - 1)).longValue() - ((Long) OutputAnalyzerNew.this.valleys.get(0)).longValue())) * 1.0f) / 1000.0f)});
                    OutputAnalyzerNew.this.sendMessage(1, format);
                    StringBuilder sb = new StringBuilder();
                    sb.append(format);
                    sb.append(OutputAnalyzerNew.this.activity.getString(R.string.row_separator));
                    sb.append(OutputAnalyzerNew.this.activity.getString(R.string.raw_values));
                    sb.append(OutputAnalyzerNew.this.activity.getString(R.string.row_separator));
                    for (int i = 0; i < stdValues.size(); i++) {
                        Measurement measurement = stdValues.get(i);
                        sb.append(new SimpleDateFormat(OutputAnalyzerNew.this.activity.getString(R.string.dateFormatGranular), Locale.getDefault()).format(measurement.timestamp));
                        sb.append(OutputAnalyzerNew.this.activity.getString(R.string.separator));
                        sb.append(measurement.measurement);
                        sb.append(OutputAnalyzerNew.this.activity.getString(R.string.row_separator));
                    }
                    sb.append(OutputAnalyzerNew.this.activity.getString(R.string.output_detected_peaks_header));
                    sb.append(OutputAnalyzerNew.this.activity.getString(R.string.row_separator));
                    Iterator it = OutputAnalyzerNew.this.valleys.iterator();
                    while (it.hasNext()) {
                        sb.append(((Long) it.next()).longValue());
                        sb.append(OutputAnalyzerNew.this.activity.getString(R.string.row_separator));
                    }
                    OutputAnalyzerNew.this.sendMessage(2, sb.toString());
                    newCameraService.stop();
                }
            }.start();
        }
    }

    public void stop() {
        CountDownTimer countDownTimer = this.timer;
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    public void sendMessage(int i, Object obj) {
        Message message = new Message();
        message.what = i;
        message.obj = obj;
        this.mainHandler.sendMessage(message);
    }
}

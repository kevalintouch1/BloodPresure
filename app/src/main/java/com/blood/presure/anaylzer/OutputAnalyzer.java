package com.blood.presure.anaylzer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.TextureView;

import androidx.vectordrawable.graphics.drawable.PathInterpolatorCompat;

import com.blood.presure.Measurement.MeasureStore;
import com.blood.presure.chart.NewImageProcessing;
import com.blood.presure.chart.NewCameraService;
import com.blood.presure.Interface.stateReport;
import com.blood.presure.Measurement.Measurement;
import com.blood.presure.activity.NewHeartRateActivityNew;
import com.blood.presure.chart.NewChartView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class OutputAnalyzer {
    NewCameraService newCameraService;
    List<Float> chartStore;
    @SuppressLint("RestrictedApi")
    private int clipLength = PathInterpolatorCompat.MAX_NUM_POINTS;
    private int detectedValleys = 0;
    public boolean fingerDetected = false;
    int fingerNotDetctedDelay = 4;
    int fingerNotDetectedTime;
    boolean firstDelayPassed = false;
    private final NewChartView graphTextureView;
    private final Handler mainHandler;
    private final stateReport reporter;
    public boolean reseted = false;
    private MeasureStore store;
    TextureView textureView;
    public int tickPassedWhileScaning = 0;
    public int ticksPassed = 0;
    private CountDownTimer timer;
    private CopyOnWriteArrayList<Long> valleys = new CopyOnWriteArrayList<>();

    public OutputAnalyzer(Activity activity2, NewChartView newChartView, Handler handler, stateReport statereport) {
        this.reporter = statereport;
        this.graphTextureView = newChartView;
        this.mainHandler = handler;
    }

    private boolean detectValley() {
        CopyOnWriteArrayList<Measurement<Integer>> lastStdValues = this.store.getLastStdValues(13);
        if (lastStdValues.size() < 13) {
            return false;
        }
        Integer num = lastStdValues.get((int) Math.ceil(6.5d)).measurement;
        Iterator<Measurement<Integer>> it = lastStdValues.iterator();
        while (it.hasNext()) {
            if (it.next().measurement.intValue() < num.intValue()) {
                return false;
            }
        }
        return !lastStdValues.get((int) Math.ceil(6.5d)).measurement.equals(lastStdValues.get(((int) Math.ceil(6.5d)) - 1).measurement);
    }

    public void measurePulse(TextureView textureView2, NewCameraService newCameraService2) {
        CountDownTimer countDownTimer = this.timer;
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        this.textureView = textureView2;
        this.newCameraService = newCameraService2;
        this.firstDelayPassed = false;
        this.store = new MeasureStore();
        this.chartStore = new ArrayList();
        for (int i = 0; i < 50; i++) {
            this.chartStore.add(Float.valueOf(0.0f));
        }
        this.valleys = new CopyOnWriteArrayList<>();
        this.detectedValleys = 0;
        sendMessage(5, 0);
        this.ticksPassed = 0;
        this.tickPassedWhileScaning = 0;
        this.timer = new CountDownTimer(15000, 45) {
            public void onTick(long j) {
                OutputAnalyzer.this.task(j);
            }

            public void onFinish() {
                OutputAnalyzer.this.finishTask();
            }
        };
        stateReport statereport = this.reporter;
        if (statereport != null) {
            statereport.report(NewHeartRateActivityNew.VIEW_STATE.MEASUREMENT);
        }
        this.timer.start();
    }

    public void finishTask() {
        this.store.getStdValues();
        if (this.valleys.size() == 0) {
            mainHandler.sendMessage(Message.obtain(mainHandler, 3, "No valleys detected - there may be an issue when accessing the camera."));
            return;
        }
        CopyOnWriteArrayList<Long> copyOnWriteArrayList = this.valleys;
        copyOnWriteArrayList.get(copyOnWriteArrayList.size() - 1).longValue();
        this.valleys.get(0).longValue();
        CopyOnWriteArrayList<Long> copyOnWriteArrayList2 = this.valleys;
        sendMessage(2, Integer.valueOf((int) ((((float) (this.detectedValleys - 1)) * 60.0f) / Math.max(1.0f, ((float) (copyOnWriteArrayList2.get(copyOnWriteArrayList2.size() - 1).longValue() - this.valleys.get(0).longValue())) / 1000.0f))));
        this.newCameraService.stop();
    }

    public void task(long j) {
        int i = this.clipLength;
        int i2 = this.ticksPassed + 1;
        this.ticksPassed = i2;
        if (i <= i2 * 45) {
            new Thread(new Runnable() {
                public void run() {
                    OutputAnalyzer.this.startAnalyzer(2000);
                }
            }).start();
        }
    }

    public void startAnalyzer(long j) {
        float f;
        float f2;
        Bitmap bitmap = this.textureView.getBitmap();
        int width = this.textureView.getWidth() * this.textureView.getHeight();
        int[] iArr = new int[width];
        bitmap.getPixels(iArr, 0, this.textureView.getWidth(), 0, 0, this.textureView.getWidth(), this.textureView.getHeight());
        if (NewImageProcessing.getAverageColorRGB(bitmap, 10, 10)[0] < 200) {
            this.chartStore.add(Float.valueOf(0.0f));
            this.fingerDetected = false;
            int i = this.fingerNotDetectedTime + 1;
            this.fingerNotDetectedTime = i;
            if (i >= this.fingerNotDetctedDelay) {
                sendMessage(4, 0);
            } else {
                cantDetectFinger();
            }
        } else {
            this.tickPassedWhileScaning++;
            if (!this.fingerDetected) {
                this.fingerDetected = true;
                this.fingerNotDetectedTime = 0;
            }
            int i2 = 0;
            for (int i3 = 0; i3 < width; i3++) {
                i2 += (iArr[i3] >> 16) & 255;
            }
            this.store.add(i2);
            if (detectValley()) {
                this.chartStore.add(Float.valueOf(0.8f));
                this.chartStore.add(Float.valueOf(-0.5f));
                this.detectedValleys++;
                this.valleys.add(Long.valueOf(this.store.getLastTimestamp().getTime()));
                long j2 = 15000 - j;
                if (this.valleys.size() == 1) {
                    f2 = ((float) this.detectedValleys) * 60.0f;
                    f = Math.max(1.0f, ((float) (j2 - ((long) this.clipLength))) / 1000.0f);
                } else {
                    f2 = ((float) (this.detectedValleys - 1)) * 60.0f;
                    CopyOnWriteArrayList<Long> copyOnWriteArrayList = this.valleys;
                    f = Math.max(1.0f, ((float) (copyOnWriteArrayList.get(copyOnWriteArrayList.size() - 1).longValue() - this.valleys.get(0).longValue())) / 1000.0f);
                }
                sendMessage(1, Integer.valueOf((int) (f2 / f)));
            } else {
                this.chartStore.add(Float.valueOf((((float) Math.random()) * 0.12f) - 0.06f));
            }
        }
        this.graphTextureView.data = this.chartStore;
    }

    private void cantDetectFinger() {
        this.timer.cancel();
        sendMessage(6, 0);
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

    public void restart() {
        measurePulse(this.textureView, this.newCameraService);
        this.reseted = true;
        this.clipLength = 1000;
    }
}

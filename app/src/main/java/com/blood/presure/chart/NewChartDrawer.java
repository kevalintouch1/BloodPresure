package com.blood.presure.chart;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;
import android.view.TextureView;

import com.blood.presure.Measurement.Measurement;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;


public class NewChartDrawer {
    private final TextureView chartTextureView;
    private final Paint fillWhite;
    private final Paint paint;

    public NewChartDrawer(TextureView textureView) {
        Paint paint2 = new Paint();
        this.paint = paint2;
        Paint paint3 = new Paint();
        this.fillWhite = paint3;
        this.chartTextureView = textureView;
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setColor(-16776961);
        paint2.setAntiAlias(true);
        paint2.setStrokeWidth(2.0f);
        paint3.setStyle(Paint.Style.FILL);
        paint3.setColor(-1);
    }


    public void draw(CopyOnWriteArrayList<Measurement<Float>> copyOnWriteArrayList) {
        Canvas lockCanvas = this.chartTextureView.lockCanvas();
        Log.e("TAG", "draw: "+lockCanvas);
        if (lockCanvas != null) {
            lockCanvas.drawPaint(this.fillWhite);
            Path path = new Path();
            float width = (float) lockCanvas.getWidth();
            float height = (float) lockCanvas.getHeight();
            int size = copyOnWriteArrayList.size();
            Iterator<Measurement<Float>> it = copyOnWriteArrayList.iterator();
            float f = Float.MAX_VALUE;
            float f2 = Float.MIN_VALUE;
            while (it.hasNext()) {
                Measurement next = it.next();
                if (((Float) next.measurement).floatValue() < f) {
                    f = ((Float) next.measurement).floatValue();
                }
                if (((Float) next.measurement).floatValue() > f2) {
                    f2 = ((Float) next.measurement).floatValue();
                }
            }
            float f3 = f2 - f;
            path.moveTo(0.0f, ((((Float) copyOnWriteArrayList.get(0).measurement).floatValue() - f) * height) / f3);
            for (int i = 1; i < size; i++) {
                path.lineTo((((float) i) * width) / ((float) size), ((((Float) copyOnWriteArrayList.get(i).measurement).floatValue() - f) * height) / f3);
            }
            lockCanvas.drawPath(path, this.paint);
            this.chartTextureView.unlockCanvasAndPost(lockCanvas);
        }
    }
}

package com.blood.presure.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.blood.presure.R;

import java.util.ArrayList;
import java.util.List;

public class NewcirclesView extends View {
    public boolean autoSpawn = true;
    Paint bpPaint;
    public int delayBetweenSpawns = 2000;
    List<circle> items = new ArrayList();
    public long lastTimeSpawned = 0;
    Paint paint;

    class circle {
        float life = 11000.0f;
        long timeMade = System.currentTimeMillis();

        circle() {
        }

        public float getProgress() {
            return ((float) (System.currentTimeMillis() - this.timeMade)) / this.life;
        }

        public boolean shouldDie() {
            return ((float) this.timeMade) + this.life < ((float) System.currentTimeMillis());
        }

        public int getAlpha() {
            getProgress();
            return (int) ((1.0f - getProgress()) * 255.0f);
        }
    }

    public NewcirclesView(Context context) {
        super(context);
        init();
    }

    public NewcirclesView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public NewcirclesView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    public NewcirclesView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        init();
    }

    public void init() {
        Paint paint2 = new Paint();
        this.paint = paint2;
        paint2.setColor(Color.parseColor("#2196F3"));
        this.paint.setStrokeWidth(13.0f);
        this.paint.setAntiAlias(true);
        this.paint.setStyle(Paint.Style.STROKE);
        Paint paint3 = new Paint();
        this.bpPaint = paint3;
        paint3.setColor(ContextCompat.getColor(getContext(), R.color.bg));
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getHeight();
        int width = getWidth();
        int i = 0;
        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        while (i < this.items.size()) {
            circle circle2 = this.items.get(i);
            if (circle2.getProgress() >= 1.0f) {
                this.items.remove(i);
                i--;
            } else {
                float progress = circle2.getProgress() * (((float) height) / 2.0f);
                this.paint.setAlpha(circle2.getAlpha());
                canvas.drawCircle((float) (height / 2), (float) (width / 2), progress, this.paint);
            }
            i++;
        }
        if (this.items.size() == 0 && this.autoSpawn) {
            spawnFirstCircles();
        }
        if (this.lastTimeSpawned + ((long) this.delayBetweenSpawns) < System.currentTimeMillis() && this.autoSpawn) {
            spawn(0, 9000);
        }
        invalidate();
    }

    private void spawnFirstCircles() {
        for (int i = 0; i < 14; i++) {
            circle circle2 = new circle();
            circle2.timeMade = System.currentTimeMillis() - ((long) (this.delayBetweenSpawns * i));
            this.items.add(circle2);
        }
        this.lastTimeSpawned = System.currentTimeMillis();
    }

    public void spawn(long j, int i) {
        circle circle2 = new circle();
        this.items.add(circle2);
        circle2.life = (float) i;
        this.lastTimeSpawned = System.currentTimeMillis() - j;
    }
}

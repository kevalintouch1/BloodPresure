package com.blood.presure.chart;

import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;

import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.buffer.BarBuffer;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.model.GradientColor;
import com.github.mikephil.charting.renderer.BarChartRenderer;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class NewCustomBarChartRender extends BarChartRenderer {
    private RectF mBarShadowRectBuffer = new RectF();
    private int mRadius;

    public NewCustomBarChartRender(BarDataProvider barDataProvider, ChartAnimator chartAnimator, ViewPortHandler viewPortHandler) {
        super(barDataProvider, chartAnimator, viewPortHandler);
    }

    public void setRadius(int i) {
        this.mRadius = i;
    }

    public void drawDataSet(Canvas canvas, IBarDataSet iBarDataSet, int i) {
        Canvas canvas2 = canvas;
        IBarDataSet iBarDataSet2 = iBarDataSet;
        int i2 = i;
        Transformer transformer = this.mChart.getTransformer(iBarDataSet.getAxisDependency());
        this.mBarBorderPaint.setColor(iBarDataSet.getBarBorderColor());
        this.mBarBorderPaint.setStrokeWidth(Utils.convertDpToPixel(iBarDataSet.getBarBorderWidth()));
        this.mShadowPaint.setColor(iBarDataSet.getBarShadowColor());
        boolean z = iBarDataSet.getBarBorderWidth() > 0.0f;
        float phaseX = this.mAnimator.getPhaseX();
        float phaseY = this.mAnimator.getPhaseY();
        if (this.mChart.isDrawBarShadowEnabled()) {
            this.mShadowPaint.setColor(iBarDataSet.getBarShadowColor());
            float barWidth = this.mChart.getBarData().getBarWidth() / 2.0f;
            double min = Math.min(Math.ceil((double) ((int) ((double) (((float) iBarDataSet.getEntryCount()) * phaseX)))), (double) iBarDataSet.getEntryCount());
            for (int i3 = 0; ((double) i3) < min; i3++) {
                float x = ((BarEntry) iBarDataSet2.getEntryForIndex(i3)).getX();
                this.mBarShadowRectBuffer.left = x - barWidth;
                this.mBarShadowRectBuffer.right = x + barWidth;
                transformer.rectValueToPixel(this.mBarShadowRectBuffer);
                if (this.mViewPortHandler.isInBoundsLeft(this.mBarShadowRectBuffer.right)) {
                    if (!this.mViewPortHandler.isInBoundsRight(this.mBarShadowRectBuffer.left)) {
                        break;
                    }
                    this.mBarShadowRectBuffer.top = this.mViewPortHandler.contentTop();
                    this.mBarShadowRectBuffer.bottom = this.mViewPortHandler.contentBottom();
                    RectF rectF = this.mBarRect;
                    float f = (float) this.mRadius;
                    canvas2.drawRoundRect(rectF, f, f, this.mShadowPaint);
                }
            }
        }
        BarBuffer barBuffer = this.mBarBuffers[i2];
        barBuffer.setPhases(phaseX, phaseY);
        barBuffer.setDataSet(i2);
        barBuffer.setInverted(this.mChart.isInverted(iBarDataSet.getAxisDependency()));
        barBuffer.setBarWidth(this.mChart.getBarData().getBarWidth());
        barBuffer.feed(iBarDataSet2);
        transformer.pointValuesToPixel(barBuffer.buffer);
        boolean z2 = iBarDataSet.getColors().size() == 1;
        if (z2) {
            this.mRenderPaint.setColor(iBarDataSet.getColor());
        }
        for (int i4 = 0; i4 < barBuffer.size(); i4 += 4) {
            int i5 = i4 + 2;
            if (this.mViewPortHandler.isInBoundsLeft(barBuffer.buffer[i5])) {
                if (this.mViewPortHandler.isInBoundsRight(barBuffer.buffer[i4])) {
                    if (!z2) {
                        this.mRenderPaint.setColor(iBarDataSet2.getColor(i4 / 4));
                    }
                    if (iBarDataSet.getGradientColor() != null) {
                        GradientColor gradientColor = iBarDataSet.getGradientColor();
                        this.mRenderPaint.setShader(new LinearGradient(barBuffer.buffer[i4], barBuffer.buffer[i4 + 3], barBuffer.buffer[i4], barBuffer.buffer[i4 + 1], gradientColor.getStartColor(), gradientColor.getEndColor(), Shader.TileMode.MIRROR));
                    }
                    if (iBarDataSet.getGradientColors() != null) {
                        int i6 = i4 / 4;
                        this.mRenderPaint.setShader(new LinearGradient(barBuffer.buffer[i4], barBuffer.buffer[i4 + 3], barBuffer.buffer[i4], barBuffer.buffer[i4 + 1], iBarDataSet2.getGradientColor(i6).getStartColor(), iBarDataSet2.getGradientColor(i6).getEndColor(), Shader.TileMode.MIRROR));
                    }
                    int i7 = i4 + 1;
                    int i8 = i4 + 3;
                    RectF rectF2 = new RectF(barBuffer.buffer[i4], barBuffer.buffer[i7], barBuffer.buffer[i5], barBuffer.buffer[i8]);
                    float f2 = (float) this.mRadius;
                    canvas2.drawPath(roundRect(rectF2, f2, f2, true, true, false, false), this.mRenderPaint);
                    if (z) {
                        RectF rectF3 = new RectF(barBuffer.buffer[i4], barBuffer.buffer[i7], barBuffer.buffer[i5], barBuffer.buffer[i8]);
                        float f3 = (float) this.mRadius;
                        canvas2.drawPath(roundRect(rectF3, f3, f3, true, true, false, false), this.mBarBorderPaint);
                    }
                } else {
                    return;
                }
            }
        }
    }

    private Path roundRect(RectF rectF, float f, float f2, boolean z, boolean z2, boolean z3, boolean z4) {
        float f3 = rectF.top;
        float f4 = rectF.left;
        float f5 = rectF.right;
        float f6 = rectF.bottom;
        Path path = new Path();
        if (f < 0.0f) {
            f = 0.0f;
        }
        if (f2 < 0.0f) {
            f2 = 0.0f;
        }
        float f7 = f5 - f4;
        float f8 = f6 - f3;
        float f9 = f7 / 2.0f;
        if (f > f9) {
            f = f9;
        }
        float f10 = f8 / 2.0f;
        if (f2 > f10) {
            f2 = f10;
        }
        float f11 = f7 - (f * 2.0f);
        float f12 = f8 - (2.0f * f2);
        path.moveTo(f5, f3 + f2);
        if (z2) {
            float f13 = -f2;
            path.rQuadTo(0.0f, f13, -f, f13);
        } else {
            path.rLineTo(0.0f, -f2);
            path.rLineTo(-f, 0.0f);
        }
        path.rLineTo(-f11, 0.0f);
        if (z) {
            float f14 = -f;
            path.rQuadTo(f14, 0.0f, f14, f2);
        } else {
            path.rLineTo(-f, 0.0f);
            path.rLineTo(0.0f, f2);
        }
        path.rLineTo(0.0f, f12);
        if (z4) {
            path.rQuadTo(0.0f, f2, f, f2);
        } else {
            path.rLineTo(0.0f, f2);
            path.rLineTo(f, 0.0f);
        }
        path.rLineTo(f11, 0.0f);
        if (z3) {
            path.rQuadTo(f, 0.0f, f, -f2);
        } else {
            path.rLineTo(f, 0.0f);
            path.rLineTo(0.0f, -f2);
        }
        path.rLineTo(0.0f, -f12);
        path.close();
        return path;
    }
}

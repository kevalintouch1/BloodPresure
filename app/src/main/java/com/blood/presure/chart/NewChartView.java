package com.blood.presure.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;


import com.blood.presure.R;

import java.util.List;

public class NewChartView extends AppCompatImageView {
    float PointDistance;
    private final Paint bgPaint = new Paint();
    public List<Float> data;
    private final Paint paint = new Paint();

    public NewChartView(Context context) {
        super(context);
        init();
    }

    public NewChartView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public NewChartView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    public void init() {
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setColor(ContextCompat.getColor(getContext(), R.color.red));
        this.paint.setAntiAlias(true);
        this.paint.setStrokeWidth(4.0f);
        this.paint.setStrokeJoin(Paint.Join.ROUND);
        this.paint.setStrokeCap(Paint.Cap.ROUND);
        this.bgPaint.setStyle(Paint.Style.FILL);
        this.bgPaint.setColor(ContextCompat.getColor(getContext(), R.color.bg));
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!(canvas == null || this.data == null)) {
            Path path = new Path();
            float width = (float) canvas.getWidth();
            this.PointDistance = width / 50.0f;
            float height = (float) canvas.getHeight();
            int size = this.data.size();
            float f = height / 2.0f;
            path.moveTo(width, f);
            int i = 0;
            for (int i2 = size - 1; i2 >= 1; i2--) {
                float f2 = width - (this.PointDistance * ((float) i));
                path.lineTo(f2, f - (this.data.get(i2).floatValue() * (height / 2.2f)));
                i++;
                if (f2 <= 0.0f) {
                    break;
                }
            }
            canvas.drawPath(path, this.paint);
        }
        invalidate();
    }
}

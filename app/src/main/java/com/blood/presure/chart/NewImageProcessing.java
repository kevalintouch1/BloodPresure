package com.blood.presure.chart;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;

public abstract class NewImageProcessing {
    public static int[] getAverageColorRGB(Bitmap bitmap, int i, int i2) {
        Bitmap resizedBitmap = getResizedBitmap(bitmap, i, i2);
        int width = resizedBitmap.getWidth();
        int height = resizedBitmap.getHeight();
        int i3 = width * height;
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        for (int i7 = 0; i7 < width; i7++) {
            for (int i8 = 0; i8 < height; i8++) {
                int pixel = resizedBitmap.getPixel(i7, i8);
                if (pixel == 0) {
                    i3--;
                } else {
                    i4 += Color.red(pixel);
                    i5 += Color.green(pixel);
                    i6 += Color.blue(pixel);
                }
            }
        }
        return new int[]{i4 / i3, i5 / i3, i6 / i3};
    }

    public static Bitmap getResizedBitmap(Bitmap bitmap, int i, int i2) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(((float) i) / ((float) width), ((float) i2) / ((float) height));
        Bitmap createBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false);
        bitmap.recycle();
        return createBitmap;
    }
}

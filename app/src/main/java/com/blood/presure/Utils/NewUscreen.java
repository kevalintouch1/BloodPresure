package com.blood.presure.Utils;

import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

public class NewUscreen {
    public static int height;
    public static int width;

    public static void Init(Context context) {
        if (width == 0 || height == 0) {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            new Point();
            width = displayMetrics.widthPixels;
            height = displayMetrics.heightPixels;
        }
    }

    public static void InitFromService(Context context) {
        Display defaultDisplay = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        width = point.x;
        height = point.y;
    }
}

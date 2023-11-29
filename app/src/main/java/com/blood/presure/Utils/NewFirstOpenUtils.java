package com.blood.presure.Utils;

import android.app.Activity;
import android.content.SharedPreferences;

public class NewFirstOpenUtils {
    public static final String CONSTANTS_LANGUAGE = "CONSTANTS_LANGUAGE";

    public static void setFirstOpenApp(Activity activity, boolean z) {
        SharedPreferences.Editor edit = activity.getSharedPreferences(activity.getPackageName(), 0).edit();
        edit.putBoolean(CONSTANTS_LANGUAGE, z);
        edit.apply();
    }

    public static boolean getFirstOpenApp(Activity activity) {
        return activity.getSharedPreferences(activity.getPackageName(), 0).getBoolean(CONSTANTS_LANGUAGE, false);
    }
}

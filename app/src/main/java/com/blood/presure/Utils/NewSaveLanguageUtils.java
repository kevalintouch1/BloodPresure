package com.blood.presure.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.blood.presure.Measurement.MBridgeConstans;

public class NewSaveLanguageUtils {
    public static String DataFileName = "magic_wallpaper";
    public static SharedPreferences Tpdata;

    public static int LoadPref(String str, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DataFileName,0);
        Tpdata = sharedPreferences;
        try {
            return Integer.parseInt(sharedPreferences.getString(str, MBridgeConstans.ENDCARD_URL_TYPE_PL));
        } catch (Exception unused) {
            return 0;
        }
    }

    public static String getLanguage(String str, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DataFileName, 0);
        Tpdata = sharedPreferences;
        return sharedPreferences.getString(str, "");
    }

    public static void saveLanguage(String str, String str2, Context context) {
        if (Tpdata == null) {
            Tpdata = context.getSharedPreferences(DataFileName, 0);
        }
        SharedPreferences.Editor edit = Tpdata.edit();
        edit.putString(str, str2);
        edit.commit();
    }

    public static void init(Context context) {
        Tpdata = context.getSharedPreferences(DataFileName, 0);
    }

    public static void destroy() {
        if (Tpdata != null) {
            Tpdata = null;
        }
    }
}

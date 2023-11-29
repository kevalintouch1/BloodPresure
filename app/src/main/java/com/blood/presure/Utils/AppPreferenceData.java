package com.blood.presure.Utils;

import android.app.Activity;
import android.content.SharedPreferences;

import com.blood.presure.NewBloodApplication;


public class AppPreferenceData {
    public static SharedPreferences _sharedPreferences;
    public static SharedPreferences.Editor editor;
    private Activity activity;

    public AppPreferenceData(Activity activity) {
        this.activity = activity;
    }

    public static SharedPreferences sharedPreferences() {
        SharedPreferences sharedPreferences = NewBloodApplication.getInstance().getSharedPreferences(NewBloodApplication.getInstance().getPackageName(), 0);
        _sharedPreferences = sharedPreferences;
        editor = sharedPreferences.edit();
        return _sharedPreferences;
    }

    public static SharedPreferences.Editor editorString(String str, String str2) {
        SharedPreferences.Editor edit = _sharedPreferences.edit();
        edit.putString(str, str2);
        edit.commit();
        return edit;
    }



    public static String getState() {
        return sharedPreferences().getString("state", "");
    }
}

package com.blood.presure.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.preference.PreferenceManager;

import com.blood.presure.Measurement.DownloadCommon;
import com.blood.presure.chart.NewLanguageEntity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.blood.presure.R;

import java.util.ArrayList;
import java.util.Locale;

public class NewLanguageUtils {
    private static final String ID_SELECTED_COUNTRY = "selected_language";
    private static final String ID_SELECTED_POSITION = "selected_position";
    private static final String PREFERENCES_COUNTRY = "LanguageUtils_Country";
    private static final String PREFERENCES_NAME = "LanguageUtils";

    public static NewLanguageEntity getSelectedLanguage(Context context) {
        String string = context.getSharedPreferences(PREFERENCES_NAME, 0).getString(ID_SELECTED_COUNTRY, (String) null);
        if (string != null) {
            return (NewLanguageEntity) new Gson().fromJson(string, new TypeToken<NewLanguageEntity>() {
            }.getType());
        }
        return new NewLanguageEntity("English", "en", "logo_england", R.drawable.logo_england);
    }

    public static void setSelectedLanguage(Context context, NewLanguageEntity newLanguageEntity) {
        SharedPreferences.Editor edit = context.getSharedPreferences(PREFERENCES_NAME, 0).edit();
        edit.putString(ID_SELECTED_COUNTRY, new Gson().toJson((Object) newLanguageEntity));
        edit.apply();
    }

    public static int getPositionCountry(Context context) {
        return context.getSharedPreferences(PREFERENCES_COUNTRY, 0).getInt(ID_SELECTED_POSITION, 0);
    }

    public static void setPositionCountry(Context context, int i) {
        SharedPreferences.Editor edit = context.getSharedPreferences(PREFERENCES_COUNTRY, 0).edit();
        edit.putInt(ID_SELECTED_POSITION, i);
        edit.apply();
    }

    public static Context onAttach(Context context) {
        return setLocale(context, getSelectedLanguage(context));
    }

    public static Context setLocale(Context context, NewLanguageEntity newLanguageEntity) {
        setSelectedLanguage(context, newLanguageEntity);
        if (Build.VERSION.SDK_INT < 24 || updateResources(context, newLanguageEntity.getCode()) == null) {
            return updateResourcesLegacy(context, newLanguageEntity.getCode());
        }
        return updateResources(context, newLanguageEntity.getCode());
    }

    public static Context updateResources(Context context, String str) {
        try {
            Locale locale = new Locale(str);
            Locale.setDefault(locale);
            Resources resources = context.getResources();
            Configuration configuration = context.getResources().getConfiguration();
            configuration.setLocale(locale);
            configuration.setLayoutDirection(locale);
            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
            return context;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Context updateResourcesLegacy(Context context, String str) {
        Locale locale = new Locale(str);
        Locale.setDefault(locale);
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        configuration.setLayoutDirection(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        return context;
    }

    public static ArrayList<NewLanguageEntity> getSupportLanguageList() {
        ArrayList<NewLanguageEntity> arrayList = new ArrayList<>();
        arrayList.add(new NewLanguageEntity("English", "en", "logo_england", R.drawable.logo_england));
        arrayList.add(new NewLanguageEntity("Français", "fr", "logo_indonesia", R.drawable.logo_france));
        arrayList.add(new NewLanguageEntity("Hindi", "hi", "logo_india", R.drawable.logo_india));
//        arrayList.add(new LanguageEntity("Portuguese", TtmlNode.TAG_BR, "logo_brazil", R.drawable.logo_brazil));
        arrayList.add(new NewLanguageEntity("Spanish", "es", "logo_spain", R.drawable.logo_spanish));
        arrayList.add(new NewLanguageEntity("Russian", "ru", "logo_spain", R.drawable.ru));
        arrayList.add(new NewLanguageEntity("German", DownloadCommon.DOWNLOAD_REPORT_DOWNLOAD_ERROR, "logo_spain", R.drawable.de));
        return arrayList;
    }

    public static boolean isInternet(Context context) {
        try {
            @SuppressLint("WrongConstant") NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void save(String str, int i, Context context) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putInt(str, i).apply();
    }
}

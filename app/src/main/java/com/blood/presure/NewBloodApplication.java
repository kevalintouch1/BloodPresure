package com.blood.presure;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import com.appizona.yehiahd.fastsave.FastSave;
import com.blood.presure.Model.NewSoundManager;
import com.blood.presure.ads.AppOpenManager;
import com.blood.presure.Utils.NewLanguageUtils;
import com.onesignal.OneSignal;
import com.onesignal.debug.LogLevel;

public class NewBloodApplication extends Application {
    static NewBloodApplication instance;
    public int selectedPage = 0;

    public static NewBloodApplication getInstance() {
        return instance;
    }


    public void onCreate() {
        super.onCreate();
        instance = this;
        NewSoundManager.getInstance();
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel notificationChannel = new NotificationChannel("channel1", "FIRSTChannel", 2);
            notificationChannel.setLockscreenVisibility(1);
            getSystemService(NotificationManager.class).createNotificationChannel(notificationChannel);
        }

        FastSave.init(this);

        new AppOpenManager(NewBloodApplication.this);

        OneSignal.getDebug().setLogLevel(LogLevel.VERBOSE);
        OneSignal.initWithContext(this, "022e51f2-5355-4638-937f-966cc12f0853");
        OneSignal.getUser().getPushSubscription().optIn();
    }
    
    public void attachBaseContext(Context context) {
        try {
            super.attachBaseContext(NewLanguageUtils.onAttach(context));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

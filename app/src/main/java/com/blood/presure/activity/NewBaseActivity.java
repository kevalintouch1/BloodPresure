package com.blood.presure.activity;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.InputDeviceCompat;

import com.blood.presure.Utils.NewLanguageUtils;
import com.blood.presure.Utils.NewHomeUtils;


public class NewBaseActivity extends AppCompatActivity {


    public void attachBaseContext(Context context) {
        super.attachBaseContext(NewLanguageUtils.onAttach(context));
    }


    public void onResume() {
        super.onResume();
        initHomeWatcher();
    }

    private void initHomeWatcher() {
        NewHomeUtils newHomeUtils = new NewHomeUtils(this);
        newHomeUtils.setOnHomePressedListener(new NewHomeUtils.OnHomeResumeListener() {
            public void onHomeLongPressed() {
            }

            public void onHomePressed() {
            }
        });
        NewHomeUtils.startHome();
    }

    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        hideSystemUI();
    }

    private void hideSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(InputDeviceCompat.SOURCE_TOUCHSCREEN);
    }


    public void onDestroy() {
        super.onDestroy();
        try {
            NewHomeUtils.stopHome();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

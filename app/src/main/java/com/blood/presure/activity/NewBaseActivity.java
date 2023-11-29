package com.blood.presure.activity;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.InputDeviceCompat;

import com.blood.presure.Utils.NewLanguageUtils;
import com.blood.presure.Utils.NewHomeUtils;


public class NewBaseActivity extends AppCompatActivity {
    private NewHomeUtils homeWatcher;


    public void attachBaseContext(Context context) {
        super.attachBaseContext(NewLanguageUtils.onAttach(context));
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        initHomeWatcher();
    }

    private void initHomeWatcher() {
        NewHomeUtils newHomeUtils = new NewHomeUtils(this);
        this.homeWatcher = newHomeUtils;
        newHomeUtils.setOnHomePressedListener(new NewHomeUtils.OnHomeResumeListener() {
            public void onHomeLongPressed() {
            }

            public void onHomePressed() {
//                FirebaseQuery.setHomeResume(BaseActivity.this, true);
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

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        try {
            NewHomeUtils.stopHome();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

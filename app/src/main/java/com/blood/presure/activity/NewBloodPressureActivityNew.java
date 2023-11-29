package com.blood.presure.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.appizona.yehiahd.fastsave.FastSave;
import com.blood.presure.Fragment.NewKnowledgeFragment;
import com.blood.presure.Utils.AdAdmob;

import com.blood.presure.Fragment.NewSettingsFragments;
import com.blood.presure.Fragment.NewTracker2Fragment;
import com.blood.presure.Utils.NewEUGeneralHelper;
import com.blood.presure.chart.NewrecordPressure;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.blood.presure.R;

public class NewBloodPressureActivityNew extends NewBaseActivity {
    public static boolean showInterOnResume = false;
    private ImageView imgBack;
    private View knowledge;
    private int lastFragment = 1;
    private LinearLayout lnBanner;
    private View settings;
    private View tracker;


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_blood_pressure);
        NewrecordPressure.loadRecords();
        this.imgBack = (ImageView) findViewById(R.id.img_back);
        this.tracker = findViewById(R.id.tracker);
        this.knowledge = findViewById(R.id.knowledge);
        this.settings = findViewById(R.id.settings);
//        this.lnBanner = (LinearLayout) findViewById(R.id.ln_banner);
//        BannerInApp.getInstance().showBanner(this, this.lnBanner, "ca-app-pub-4227016782733744/8362758792");
        this.tracker.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                NewBloodPressureActivityNew.this.showTracker();
            }
        });
        this.knowledge.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                NewBloodPressureActivityNew.this.showKnowledge();
            }
        });
        this.settings.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                NewBloodPressureActivityNew.this.showSettings();
            }
        });
        this.imgBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                new AdAdmob(NewBloodPressureActivityNew.this).ShowAds(NewBloodPressureActivityNew.this);
                NewBloodPressureActivityNew.this.finish();
            }
        });

        LinearLayout adContainer = (LinearLayout) findViewById(R.id.adView);
        AdView adView = new AdView(this);
        adView.setAdUnitId(FastSave.getInstance().getString("BANNER", ""));
        adContainer.addView(adView);
        AdSize adSize = getAdSize(this);
        adView.setAdSize(adSize);
        adView.loadAd(new AdRequest.Builder().build());

    }

    private AdSize getAdSize(Activity mActivity) {


        Display display = mActivity.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;

        int adWidth = (int) (widthPixels / density);
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(mActivity, adWidth);
    }

    public void onResume() {
        super.onResume();
        new AdAdmob(this).LoadAdMobInterstitialAd(this);
        int i = this.lastFragment;
        if (i == 1) {
            showTracker();
        } else if (i == 2) {
            showKnowledge();
        } else if (i == 4) {
            showSettings();
        }
        showInterOnResume = false;
    }


    public void showSettings() {
        this.lastFragment = 4;
        this.settings.setAlpha(1.0f);
        this.knowledge.setAlpha(0.5f);
        this.tracker.setAlpha(0.5f);
        removeAllFragments();
        getSupportFragmentManager().beginTransaction().add((int) R.id.fragment_container_view, (Fragment) new NewSettingsFragments()).commitAllowingStateLoss();
        new AdAdmob(NewBloodPressureActivityNew.this).ShowAds(NewBloodPressureActivityNew.this);
    }


    public void showKnowledge() {
        this.lastFragment = 2;
        this.knowledge.setAlpha(1.0f);
        this.settings.setAlpha(0.5f);
        this.tracker.setAlpha(0.5f);
        removeAllFragments();
        getSupportFragmentManager().beginTransaction().add((int) R.id.fragment_container_view, (Fragment) new NewKnowledgeFragment()).commitAllowingStateLoss();
        new AdAdmob(NewBloodPressureActivityNew.this).ShowAds(NewBloodPressureActivityNew.this);
    }

    private void removeAllFragments() {
        for (Fragment remove : getSupportFragmentManager().getFragments()) {
            getSupportFragmentManager().beginTransaction().remove(remove).commitAllowingStateLoss();
        }
    }


    public void showTracker() {
        this.lastFragment = 1;
        this.settings.setAlpha(0.5f);
        this.knowledge.setAlpha(0.5f);
        this.tracker.setAlpha(1.0f);
        removeAllFragments();
        getSupportFragmentManager().beginTransaction().add((int) R.id.fragment_container_view, (Fragment) new NewTracker2Fragment()).commitAllowingStateLoss();
        new AdAdmob(NewBloodPressureActivityNew.this).ShowAds(NewBloodPressureActivityNew.this);
    }

    public void changeStatusBarColor(int i) {
        Window window = getWindow();
        window.addFlags(Integer.MIN_VALUE);
        window.setStatusBarColor(ContextCompat.getColor(this, i));
    }

    public void onBackPressed() {
        finish();
    }
}

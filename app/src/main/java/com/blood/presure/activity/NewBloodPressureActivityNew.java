package com.blood.presure.activity;

import static com.blood.presure.ads.AdmobAdsHelper.LoadAdMobInterstitialAd;
import static com.blood.presure.ads.AdmobAdsHelper.ShowFullAds;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.blood.presure.Fragment.NewKnowledgeFragment;
import com.blood.presure.Fragment.NewSettingsFragments;
import com.blood.presure.Fragment.NewTracker2Fragment;
import com.blood.presure.R;
import com.blood.presure.ads.AdmobAdsHelper;
import com.blood.presure.chart.NewrecordPressure;

public class NewBloodPressureActivityNew extends NewBaseActivity {
    public static boolean showInterOnResume = false;
    private View knowledge;
    private int lastFragment = 1;
    private View settings;
    private View tracker;


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_blood_pressure);
        NewrecordPressure.loadRecords();
        ImageView imgBack = findViewById(R.id.img_back);
        this.tracker = findViewById(R.id.tracker);
        this.knowledge = findViewById(R.id.knowledge);
        this.settings = findViewById(R.id.settings);
        this.tracker.setOnClickListener(view -> NewBloodPressureActivityNew.this.showTracker());
        this.knowledge.setOnClickListener(view -> NewBloodPressureActivityNew.this.showKnowledge());
        this.settings.setOnClickListener(view -> NewBloodPressureActivityNew.this.showSettings());
        imgBack.setOnClickListener(view -> {
            onBackPressed();
        });

        new AdmobAdsHelper(this).bannerAds(this, findViewById(R.id.adView));
        LoadAdMobInterstitialAd(this);
    }

    public void onResume() {
        super.onResume();
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
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container_view, new NewSettingsFragments()).commitAllowingStateLoss();
        ShowFullAds(this);
    }


    public void showKnowledge() {
        this.lastFragment = 2;
        this.knowledge.setAlpha(1.0f);
        this.settings.setAlpha(0.5f);
        this.tracker.setAlpha(0.5f);
        removeAllFragments();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container_view, new NewKnowledgeFragment()).commitAllowingStateLoss();
        ShowFullAds(this);
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
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container_view, new NewTracker2Fragment()).commitAllowingStateLoss();
        ShowFullAds(this);
    }

    public void onBackPressed() {
        ShowFullAds(this);
        finish();
    }
}

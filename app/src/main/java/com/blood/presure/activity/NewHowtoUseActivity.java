package com.blood.presure.activity;

import static com.blood.presure.ads.AdmobAdsHelper.LoadAdMobInterstitialAd;
import static com.blood.presure.ads.AdmobAdsHelper.ShowFullAds;


import android.os.Bundle;
import android.view.View;

import com.blood.presure.R;
import com.blood.presure.ads.AdmobAdsHelper;


public class NewHowtoUseActivity extends NewBaseActivity {

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_how_to_use);
        initView();
        new AdmobAdsHelper(this).bannerAds(this, findViewById(R.id.adView));
        LoadAdMobInterstitialAd(this);
    }

    private void initView() {
        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        ShowFullAds(this);
        finish();
    }
}

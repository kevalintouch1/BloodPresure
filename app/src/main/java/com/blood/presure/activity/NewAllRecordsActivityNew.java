package com.blood.presure.activity;

import static com.blood.presure.ads.AdmobAdsHelper.LoadAdMobInterstitialAd;
import static com.blood.presure.ads.AdmobAdsHelper.ShowFullAds;


import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blood.presure.Adapter.NewRecordsAdapters;
import com.blood.presure.R;
import com.blood.presure.ads.AdmobAdsHelper;
import com.blood.presure.chart.NewrecordPressure;

public class NewAllRecordsActivityNew extends NewBaseActivity {
    private RecyclerView recyclerView;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.new_all_records_activity);
        this.recyclerView = findViewById(R.id.recyclerView);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        findViewById(R.id.close).setOnClickListener(view -> onBackPressed());
        new AdmobAdsHelper(this).bannerAds(this, findViewById(R.id.adView));
        LoadAdMobInterstitialAd(this);
    }


    public void onResume() {
        super.onResume();
        this.recyclerView.setAdapter(new NewRecordsAdapters(NewrecordPressure.loadRecords(), obj -> {
            NewAddRecordActivityNew.edit = (NewrecordPressure) obj;
            NewAllRecordsActivityNew.this.startActivity(new Intent(NewAllRecordsActivityNew.this, NewAddRecordActivityNew.class));
            ShowFullAds(NewAllRecordsActivityNew.this);
        }, this));
    }

    @Override
    public void onBackPressed() {
        ShowFullAds(this);
        finish();
    }
}

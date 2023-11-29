package com.blood.presure.activity;



import static com.blood.presure.ads.AdmobAdsHelper.ShowFullAds;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appizona.yehiahd.fastsave.FastSave;
import com.blood.presure.ads.AdmobAdsHelper;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.blood.presure.R;

public class NewResultHeartActivity extends NewBaseActivity implements View.OnClickListener {
    private ImageView imgBack;
    private TextView tvRecheck;
    private TextView tvSave;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_result_heart);
        initViews();
        initEvent();
        new AdmobAdsHelper(this).bannerAds(this, findViewById(R.id.adView));
        AdmobAdsHelper.LoadAdMobInterstitialAd(this);
    }

    private void initViews() {
        this.imgBack = findViewById(R.id.img_back);
        TextView tvResultNumber = findViewById(R.id.tv_result_number);
        this.tvRecheck = findViewById(R.id.tv_recheck);
        this.tvSave = findViewById(R.id.tv_save);
        if (getIntent() != null) {
            tvResultNumber.setText(getIntent().getStringExtra("name_result"));
        }
    }

    private void initEvent() {
        this.imgBack.setOnClickListener(this);
        this.tvSave.setOnClickListener(this);
        this.tvRecheck.setOnClickListener(this);
    }

    @SuppressLint("WrongConstant")
    public void onClick(View view) {
        if (view == this.imgBack) {
            onBackPressed();
        } else if (view == this.tvSave) {
            onBackPressed();
        } else if (view == this.tvRecheck) {
            Toast.makeText(this, "Coming Soon!", 0).show();
        }
    }

    @Override
    public void onBackPressed() {
        ShowFullAds(this);
        finish();
    }
}

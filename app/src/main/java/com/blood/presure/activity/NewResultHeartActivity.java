package com.blood.presure.activity;

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
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.blood.presure.R;

public class NewResultHeartActivity extends NewBaseActivity implements View.OnClickListener {
    private ImageView imgBack;
    private LinearLayout lnBanner;
    private TextView tvRecheck;
    private TextView tvResultNumber;
    private TextView tvSave;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_result_heart);
        initViews();
        initEvent();

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


    private void initViews() {
        this.imgBack = (ImageView) findViewById(R.id.img_back);
        this.tvResultNumber = (TextView) findViewById(R.id.tv_result_number);
        this.tvRecheck = (TextView) findViewById(R.id.tv_recheck);
        this.tvSave = (TextView) findViewById(R.id.tv_save);
//        this.lnBanner = (LinearLayout) findViewById(R.id.ln_banner);
//        BannerInApp.getInstance().showBanner(this, this.lnBanner, "ca-app-pub-4227016782733744/1441583023");
        if (getIntent() != null) {
            this.tvResultNumber.setText(getIntent().getStringExtra("name_result"));
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
            NewResultHeartActivity.this.finish();
        } else if (view == this.tvSave) {
            NewResultHeartActivity.this.finish();
        } else if (view == this.tvRecheck) {
            Toast.makeText(this, "Coming Soon!", 0).show();
        }
    }
}

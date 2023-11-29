package com.blood.presure.activity;



import static com.blood.presure.ads.AdmobAdsHelper.ShowFullAds;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blood.presure.Measurement.MBridgeConstans;
import com.blood.presure.R;
import com.blood.presure.ads.AdmobAdsHelper;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

public class NewWebViewActivity extends NewBaseActivity {
    private LinearLayout lnBanner;
    private String path;
    private String strThumb;
    private String strTitle;
    private ImageView thumb;
    private TextView title;
    private WebView webView;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_web_view);
        this.title = findViewById(R.id.title);
        this.thumb = findViewById(R.id.thumb);
        this.webView = findViewById(R.id.webView);
        if (getIntent() != null) {
            this.path = getIntent().getStringExtra(MBridgeConstans.DYNAMIC_VIEW_WX_PATH);
            this.strTitle = getIntent().getStringExtra("title");
            this.strThumb = getIntent().getStringExtra("thumb");
        }
        loadPage();
        findViewById(R.id.back).setOnClickListener(view -> onBackPressed());

        new AdmobAdsHelper(this).bannerAds(this, findViewById(R.id.adView));
        AdmobAdsHelper.LoadAdMobInterstitialAd(this);
    }

    private void loadPage() {
        WebView webView2 = this.webView;
        webView2.loadUrl("file:///android_asset/" + this.path + "/page.html");
        RequestManager with = Glide.with(this);
        with.load("file:///android_asset/" + this.strThumb).into(this.thumb);
        this.title.setText(this.strTitle.replace("qst", "?"));
    }

    @Override
    public void onBackPressed() {
        ShowFullAds(this);
        finish();
    }
}

package com.blood.presure.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.appizona.yehiahd.fastsave.FastSave;
import com.blood.presure.Measurement.MBridgeConstans;
import com.blood.presure.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

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
        setContentView((int) R.layout.activity_web_view);
        this.title = (TextView) findViewById(R.id.title);
        this.thumb = (ImageView) findViewById(R.id.thumb);
        this.webView = (WebView) findViewById(R.id.webView);
//        this.lnBanner = (LinearLayout) findViewById(R.id.ln_banner);
//        BannerInApp.getInstance().showBanner(this, this.lnBanner, FirebaseQuery.getIdBannerGA(this));
        if (getIntent() != null) {
            this.path = getIntent().getStringExtra(MBridgeConstans.DYNAMIC_VIEW_WX_PATH);
            this.strTitle = getIntent().getStringExtra("title");
            this.strThumb = getIntent().getStringExtra("thumb");
        }
        loadPage();
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                NewWebViewActivity.this.finish();
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

    private void loadPage() {
        WebView webView2 = this.webView;
        webView2.loadUrl("file:///android_asset/" + this.path + "/page.html");
        RequestManager with = Glide.with((FragmentActivity) this);
        with.load("file:///android_asset/" + this.strThumb).into(this.thumb);
        this.title.setText(this.strTitle.replace("qst", "?"));
    }
}

package com.blood.presure.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.media3.common.util.UnstableApi;

import com.appizona.yehiahd.fastsave.FastSave;
import com.blood.presure.R;
import com.blood.presure.Utils.AdAdmob;
import com.blood.presure.Utils.NewEUGeneralHelper;
import com.blood.presure.Utils.NewSaveLanguageUtils;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.google.android.gms.tasks.Task;
import com.google.android.play.core.review.ReviewException;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.review.model.ReviewErrorCode;

@UnstableApi
public class NewHomeActivity extends NewBaseActivity implements View.OnClickListener {
    private ImageView imgHowToUse;

    private LinearLayout lnNative;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_home);
        this.imgHowToUse = (ImageView) findViewById(R.id.img_how_to_use);
        findViewById(R.id.linearLayout5).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                NewHomeActivity.this.openHeartRate();
            }
        });
        findViewById(R.id.linearLayout7).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                NewHomeActivity.this.openBPM();
            }
        });

        this.imgHowToUse.setOnClickListener(this);
        updateLang();

        refreshAd();
    }

    @Override
    public void onResume() {
        super.onResume();
        new AdAdmob(NewHomeActivity.this).LoadAdMobInterstitialAd(NewHomeActivity.this);

        ReviewManager manager = ReviewManagerFactory.create(this);
        Task<ReviewInfo> request = manager.requestReviewFlow();
        request.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // We can get the ReviewInfo object
                ReviewInfo reviewInfo = task.getResult();
                Task<Void> flow = manager.launchReviewFlow(this, reviewInfo);
                flow.addOnCompleteListener(task1 -> {});
            } else {
                // There was some problem, log or handle the error code.
                @ReviewErrorCode int reviewErrorCode = ((ReviewException) task.getException()).getErrorCode();
            }
        });
    }

    public void updateLang() {
        if (NewSaveLanguageUtils.getLanguage("languageTitle", this).length() == 0) {
            NewSaveLanguageUtils.saveLanguage("lang", "1", this);
            NewSaveLanguageUtils.saveLanguage("language", "en", this);
            NewSaveLanguageUtils.saveLanguage("languageTitle", "English", this);
        }
    }

    public void openHeartRate() {
        Intent intent;
        new Intent(this, NewLanguageActivity.class);
        if (NewSaveLanguageUtils.LoadPref("me", this) == 0) {
            intent = new Intent(this, NewInfoActivity.class);
        } else {
            intent = new Intent(this, NewHeartRateActivityNew.class);
        }
        startActivity(intent);
        new AdAdmob(NewHomeActivity.this).ShowAds(NewHomeActivity.this);
    }

    public void openBPM() {
        startActivity(new Intent(this, NewBloodPressureActivityNew.class));
        new AdAdmob(NewHomeActivity.this).ShowAds(NewHomeActivity.this);
    }

    public void onClick(View view) {
        if (view == this.imgHowToUse) {
            NewHomeActivity.this.startActivity(new Intent(NewHomeActivity.this, NewHowtoUseActivity.class));
            new AdAdmob(NewHomeActivity.this).ShowAds(NewHomeActivity.this);
        }
    }

    private void refreshAd() {
        String admonnative = FastSave.getInstance().getString("NATIVE", "");
        AdLoader.Builder builder = new AdLoader.Builder(this, admonnative)
                .forNativeAd(nativeAd -> {
                    LinearLayout frameLayout = findViewById(R.id.native_ads_1);
                    NativeAdView adView = (NativeAdView) getLayoutInflater()
                            .inflate(R.layout.ad_unit1, null);
                    populateUnifiedNativeAdView(nativeAd, adView);
                    frameLayout.removeAllViews();
                    frameLayout.addView(adView);
                });

        AdLoader adLoader = builder.withAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
            }
        }).build();

        adLoader.loadAd(new AdRequest.Builder().build());
    }

    private void populateUnifiedNativeAdView(NativeAd nativeAd, NativeAdView adView) {
        MediaView mediaView = adView.findViewById(R.id.ad_media);
        adView.setMediaView(mediaView);

        TextView headlineView = adView.findViewById(R.id.ad_headline);
        TextView ad_call_to_action = adView.findViewById(R.id.ad_call_to_action);
        TextView ad_body = adView.findViewById(R.id.ad_body);
        adView.setBodyView(ad_body);
        ImageView ad_app_icon = adView.findViewById(R.id.ad_app_icon);
        headlineView.setText(nativeAd.getHeadline());

        if (nativeAd.getBody() == null) {
            ad_body.setVisibility(View.INVISIBLE);
        } else {
            ad_body.setVisibility(View.VISIBLE);
            ad_body.setText(nativeAd.getBody());
        }

        if (nativeAd.getCallToAction() == null) {
            ad_call_to_action.setVisibility(View.INVISIBLE);
        } else {
            ad_call_to_action.setVisibility(View.VISIBLE);
            ad_call_to_action.setText(nativeAd.getCallToAction());
        }

        if (nativeAd.getIcon() == null) {
            ad_app_icon.setVisibility(View.GONE);
        } else {
            ad_app_icon.setImageDrawable(nativeAd.getIcon().getDrawable());
            ad_app_icon.setVisibility(View.VISIBLE);
        }
        adView.setNativeAd(nativeAd);
    }


    public void onBackPressed() {
        finish();
    }
}

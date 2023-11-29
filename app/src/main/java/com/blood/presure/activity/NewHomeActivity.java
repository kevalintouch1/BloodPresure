package com.blood.presure.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.media3.common.util.UnstableApi;

import com.blood.presure.R;
import com.blood.presure.Utils.NewSaveLanguageUtils;
import com.blood.presure.ads.AdmobAdsHelper;
import com.blood.presure.ads.Helper;
import com.google.android.gms.tasks.Task;
import com.google.android.play.core.review.ReviewException;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.review.model.ReviewErrorCode;

@UnstableApi
public class NewHomeActivity extends NewBaseActivity implements View.OnClickListener {
    private ImageView imgHowToUse;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_home);
        this.imgHowToUse = findViewById(R.id.img_how_to_use);
        findViewById(R.id.linearLayout5).setOnClickListener(view -> NewHomeActivity.this.openHeartRate());
        findViewById(R.id.linearLayout7).setOnClickListener(view -> NewHomeActivity.this.openBPM());

        this.imgHowToUse.setOnClickListener(this);
        updateLang();

        new AdmobAdsHelper(this).loadgoogleNative(this,findViewById(R.id.native_ads_1));
        AdmobAdsHelper.LoadAdMobInterstitialAd(this);
    }

    @Override
    public void onResume() {
        super.onResume();

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
        AdmobAdsHelper.ShowFullAds(NewHomeActivity.this);
    }

    public void openBPM() {
        startActivity(new Intent(this, NewBloodPressureActivityNew.class));
        AdmobAdsHelper.ShowFullAds(NewHomeActivity.this);
    }

    public void onClick(View view) {
        if (view == this.imgHowToUse) {
            NewHomeActivity.this.startActivity(new Intent(NewHomeActivity.this, NewHowtoUseActivity.class));
            AdmobAdsHelper.ShowFullAds(NewHomeActivity.this);
        }
    }

    public void onBackPressed() {
        Helper.openExitDialog(this);
    }
}

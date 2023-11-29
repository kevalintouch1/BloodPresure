package com.blood.presure.Utils;


import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.appizona.yehiahd.fastsave.FastSave;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;


public class AdAdmob {
    public static int showAdsNumberCount = 1;

    public AdAdmob(Activity activity) {
        MobileAds.initialize(activity, initializationStatus -> {
        });
    }


    static InterstitialAd ad_mob_interstitial;
    AdRequest interstitial_adRequest;

    public void LoadAdMobInterstitialAd(Activity activity) {
        // TODO Auto-generated method stub
        try {
            Bundle non_personalize_bundle = new Bundle();
            non_personalize_bundle.putString("npa", "1");

            interstitial_adRequest = new AdRequest.Builder().build();

            InterstitialAd.load(activity, FastSave.getInstance().getString("INTER", ""), interstitial_adRequest, new InterstitialAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                    ad_mob_interstitial = interstitialAd;
                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    ad_mob_interstitial = null;
                }
            });
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public void ShowAds(Activity activity) {
        InterstitialAd interstitialAd = ad_mob_interstitial;
        if (interstitialAd != null) {
            interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdFailedToShowFullScreenContent(AdError adError) {
                }

                @Override
                public void onAdDismissedFullScreenContent() {
                }

                @Override
                public void onAdShowedFullScreenContent() {
                    ad_mob_interstitial = null;
                }
            });
        }
        int i = showAdsNumberCount + 1;
        showAdsNumberCount = i;
        if (FastSave.getInstance().getInt("CLICKS", -1) < i) {
            if (ad_mob_interstitial != null) {
                ad_mob_interstitial.show(activity);
            }
            showAdsNumberCount = 0;
        }
    }
}
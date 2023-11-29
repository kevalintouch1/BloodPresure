package com.blood.presure.ads;


import static com.blood.presure.ads.Helper.showAdsNumberCount;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.appizona.yehiahd.fastsave.FastSave;
import com.blood.presure.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;

public class AdmobAdsHelper {
    final Context context;

    public AdmobAdsHelper(Context context) {
        this.context = context;
    }

    public void bannerAds(Context mContext, ViewGroup frameLayout) {
        if (FastSave.getInstance().getBoolean("isGoogleBanner", false)) {
            String admonnative = FastSave.getInstance().getString("BANNER", "");

            Bundle bundle = new Bundle();
            bundle.putString("npa", "1");
            AdRequest banner_adRequest = new AdRequest.Builder().build();
            AdView adView = new AdView(mContext);
            adView.setAdSize(AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(mContext, mContext.getResources().getConfiguration().screenWidthDp));
            adView.setAdUnitId(admonnative);
            adView.loadAd(banner_adRequest);
            frameLayout.addView(adView);
            adView.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    frameLayout.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    Log.e("TAG", "onAdFailedToLoad: " + loadAdError.getMessage());
                    frameLayout.setVisibility(View.GONE);
                }
            });
        }

    }

    public void rectAds(Context mContext, ViewGroup frameLayout) {
        if (FastSave.getInstance().getBoolean("isGoogleBanner", false)) {
            String admonnative = FastSave.getInstance().getString("BANNER", "");

            Bundle bundle = new Bundle();
            bundle.putString("npa", "1");
            AdRequest banner_adRequest = new AdRequest.Builder().build();
            AdView adView = new AdView(mContext);
            adView.setAdSize(AdSize.MEDIUM_RECTANGLE);
            adView.setAdUnitId(admonnative);
            adView.loadAd(banner_adRequest);
            frameLayout.addView(adView);
        }
    }

    public void loadgoogleNative(Context context, ViewGroup viewGroup) {
        if (FastSave.getInstance().getBoolean("isGoogleNativeAdvanced", false)) {
            AdLoader.Builder builder = new AdLoader.Builder(context, FastSave.getInstance().getString("NATIVE", ""))
                    .forNativeAd(nativeAd -> {
                        NativeAdView adView = (NativeAdView) ((Activity) context).getLayoutInflater()
                                .inflate(R.layout.native_ad_unified, null);
                        populateUnifiedNativeAdView(nativeAd, adView);
                        viewGroup.removeAllViews();
                        viewGroup.addView(adView);
                    });

            AdLoader adLoader = builder.withAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    Log.e("TAG", "onAdFailedToLoad: " + loadAdError.getMessage());
                    super.onAdFailedToLoad(loadAdError);
                }
            }).build();

            adLoader.loadAd(new AdRequest.Builder().build());
        }
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

        adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setStoreView(adView.findViewById(R.id.ad_store));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));

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


    public void nativeSmall(ViewGroup layout_ads) {
        String admonnative = FastSave.getInstance().getString("NATIVE", "");
        Log.e("TAG", "nativerAds: " + admonnative);
        new AdLoader.Builder(context, admonnative)
                .forNativeAd(nativeAd -> {
                    if (layout_ads != null) {
                        layout_ads.removeAllViews();
                        NativeAdView unifiedNativeAdView = (NativeAdView) LayoutInflater.from(context).inflate(R.layout.custom_native_small, null);
                        layout_ads.addView(unifiedNativeAdView);
                        populateMusic(nativeAd, unifiedNativeAdView);
                    }
                }).withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        layout_ads.removeAllViews();
                        Log.e("TAG", "bannerAds: " + loadAdError.getMessage());
                    }
                }).build().loadAd(new AdRequest.Builder().build());
    }

    public void populateMusic(NativeAd unifiedNativeAd, NativeAdView unifiedNativeAdView) {
        unifiedNativeAdView.setHeadlineView(unifiedNativeAdView.findViewById(R.id.ad_headline));
        unifiedNativeAdView.setBodyView(unifiedNativeAdView.findViewById(R.id.ad_body));
        unifiedNativeAdView.setCallToActionView(unifiedNativeAdView.findViewById(R.id.ad_call_to_action));
        unifiedNativeAdView.setIconView(unifiedNativeAdView.findViewById(R.id.ad_app_icon));
        try {
            ((TextView) unifiedNativeAdView.getHeadlineView()).setText(unifiedNativeAd.getHeadline());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (unifiedNativeAd.getBody() == null) {
                unifiedNativeAdView.getBodyView().setVisibility(View.INVISIBLE);
            } else {
                unifiedNativeAdView.getBodyView().setVisibility(View.VISIBLE);
                ((TextView) unifiedNativeAdView.getBodyView()).setText(unifiedNativeAd.getBody());
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            if (unifiedNativeAd.getCallToAction() == null) {
                unifiedNativeAdView.getCallToActionView().setVisibility(View.INVISIBLE);
            } else {
                unifiedNativeAdView.getCallToActionView().setVisibility(View.VISIBLE);
                ((TextView) unifiedNativeAdView.getCallToActionView()).setText(unifiedNativeAd.getCallToAction());
            }
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        try {
            if (unifiedNativeAd.getIcon() == null) {
                unifiedNativeAdView.getIconView().setVisibility(View.GONE);
            } else {
                ((ImageView) unifiedNativeAdView.getIconView()).setImageDrawable(unifiedNativeAd.getIcon().getDrawable());
                unifiedNativeAdView.getIconView().setVisibility(View.VISIBLE);
            }
        } catch (Exception e4) {
            e4.printStackTrace();
        }
        unifiedNativeAdView.setNativeAd(unifiedNativeAd);
    }


    static InterstitialAd ad_mob_interstitial;
    static AdRequest interstitial_adRequest;

    public static void LoadAdMobInterstitialAd(Context context) {
        if (FastSave.getInstance().getBoolean("isGoogleInterstitial", false)) {
            try {
                Bundle non_personalize_bundle = new Bundle();
                non_personalize_bundle.putString("npa", "1");

                interstitial_adRequest = new AdRequest.Builder().build();

                InterstitialAd.load(context, FastSave.getInstance().getString("INTER", ""), interstitial_adRequest, new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        ad_mob_interstitial = interstitialAd;
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        ad_mob_interstitial = null;
                        LoadAdMobInterstitialAd(context);
                    }
                });
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
    }

    public static void ShowFullAds(final Context context) {
        InterstitialAd interstitialAd = ad_mob_interstitial;
        if (interstitialAd != null) {
            interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdFailedToShowFullScreenContent(com.google.android.gms.ads.AdError adError) {
                    LoadAdMobInterstitialAd(context);
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
        if (FastSave.getInstance().getInt("CLICKS", 0) < i) {
            if (ad_mob_interstitial != null) {
                ad_mob_interstitial.show((Activity) context);
            }
            Helper.is_show_open_ad = false;
            showAdsNumberCount = 0;
        }
    }

}

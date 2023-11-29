package com.blood.presure.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.util.UnstableApi;

import com.appizona.yehiahd.fastsave.BuildConfig;
import com.appizona.yehiahd.fastsave.FastSave;
import com.blood.presure.Utils.AppUtil;
import com.blood.presure.Utils.NewLanguageUtils;
import com.blood.presure.Utils.New_Helper;
import com.blood.presure.Utils.NewFirstOpenUtils;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.blood.presure.R;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


@UnstableApi
public class NewSplashActivity extends AppCompatActivity {
    String googleAppOpen;
    String googleInterstitial;
    String googleNativeAdvanced;
    String googleBanner;
    String fbBanner;
    String fbInterstitial;
    String fbRewarded;
    String fbNativeAdvanced;
    boolean isGoogleAppOpen, isGoogleBanner, isGoogleInterstitial, isGoogleRewarded, isGoogleNativeAdvanced;
    boolean isFbAppOpen, isFbBanner, isFbInterstitial, isFbRewarded, isFbNativeAdvanced;
    public void onBackPressed() {
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_splash);

        if (AppUtil.isNetworkAvailable(this)) {
            getdata();
        } else {
            new Handler().postDelayed(() -> {
                nextActivity();
            }, 1000);
        }
    }

    private void getdata() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        String getdata_url = "http://139.59.232.13/api/v1/applist/" + getPackageName();
        client.get(getdata_url, params, new AsyncHttpResponseHandler() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.e("TAG", statusCode + " > " + new String(responseBody));
                try {
                    JSONObject jsonObject2 = new JSONObject(new String(responseBody));
                    JSONObject dataJsonObject = jsonObject2.getJSONObject("data");
                    JSONObject AppSettingJsonObject = dataJsonObject.getJSONObject("AppSetting");
                    JSONArray googlejsonArray = dataJsonObject.getJSONArray("GoogleAds");
                    JSONObject AppAdJsonObject = dataJsonObject.getJSONObject("AppAd");
                    for (int i = 0; i < googlejsonArray.length(); i++) {
                        JSONObject jSONObject = googlejsonArray.getJSONObject(i);
                        googleAppOpen = jSONObject.getString("googleAppOpen");
                        googleInterstitial = jSONObject.getString("googleInterstitial");
                        googleNativeAdvanced = jSONObject.getString("googleNativeAdvanced");
                        googleBanner= jSONObject.getString("googleBanner");

                        isGoogleAppOpen = jSONObject.getBoolean("isGoogleAppOpen");
                        isGoogleBanner = jSONObject.getBoolean("isGoogleBanner");
                        isGoogleInterstitial = jSONObject.getBoolean("isGoogleInterstitial");
                        isGoogleRewarded = jSONObject.getBoolean("isGoogleRewarded");
                        isGoogleNativeAdvanced = jSONObject.getBoolean("isGoogleNativeAdvanced");
                    }

                    JSONArray fbjsonArray = dataJsonObject.getJSONArray("FacebookAds");
                    for (int i = 0; i < fbjsonArray.length(); i++) {
                        JSONObject jSONObject = fbjsonArray.getJSONObject(i);
                        fbBanner = jSONObject.getString("fbBanner");
                        fbInterstitial = jSONObject.getString("fbInterstitial");
                        fbRewarded = jSONObject.getString("fbRewarded");
                        fbNativeAdvanced = jSONObject.getString("fbNativeAdvanced");

                        isFbAppOpen = jSONObject.getBoolean("isFbAppOpen");
                        isFbBanner = jSONObject.getBoolean("isFbBanner");
                        isFbInterstitial = jSONObject.getBoolean("isFbInterstitial");
                        isFbRewarded = jSONObject.getBoolean("isFbRewarded");
                        isFbNativeAdvanced = jSONObject.getBoolean("isFbNativeAdvanced");
                    }

                    int appVersion = Integer.parseInt(AppSettingJsonObject.getString("appVersion"));
                    boolean isUpdate = AppSettingJsonObject.getBoolean("isUpdate");
                    String privacy_policy = AppSettingJsonObject.getString("privacyPolicyUrl");
                    String aboutUsUrl = AppSettingJsonObject.getString("aboutUsUrl");
                    int clicks = AppAdJsonObject.getInt("adInnerClickCount");
                    boolean showAd = AppAdJsonObject.getBoolean("showAd");

                    FastSave.getInstance().saveBoolean("SHOWADS", showAd);
                    FastSave.getInstance().saveString("ABOUT", aboutUsUrl);
                    FastSave.getInstance().saveString("PRIVACY_POLICY", privacy_policy);
                    FastSave.getInstance().saveInt("CLICKS", clicks);

//                    FastSave.getInstance().saveString("APP_OPEN", googleAppOpen);
//                    FastSave.getInstance().saveString("INTER", googleInterstitial);
//                    FastSave.getInstance().saveString("NATIVE", googleNativeAdvanced);
//                    FastSave.getInstance().saveString("BANNER", googleBanner);

                    FastSave.getInstance().saveString("APP_OPEN", "ca-app-pub-3940256099942544/9257395921");
                    FastSave.getInstance().saveString("INTER", "ca-app-pub-3940256099942544/1033173712");
                    FastSave.getInstance().saveString("NATIVE", "ca-app-pub-3940256099942544/2247696110");
                    FastSave.getInstance().saveString("BANNER", "ca-app-pub-3940256099942544/6300978111");

                    FastSave.getInstance().saveBoolean("isGoogleAppOpen", isGoogleAppOpen);
                    FastSave.getInstance().saveBoolean("isGoogleBanner", isGoogleBanner);
                    FastSave.getInstance().saveBoolean("isGoogleInterstitial", isGoogleInterstitial);
                    FastSave.getInstance().saveBoolean("isGoogleRewarded", isGoogleRewarded);
                    FastSave.getInstance().saveBoolean("isGoogleNativeAdvanced", isGoogleNativeAdvanced);

                    FastSave.getInstance().saveBoolean("isFbAppOpen", isFbAppOpen);
                    FastSave.getInstance().saveBoolean("isFbBanner", isFbBanner);
                    FastSave.getInstance().saveBoolean("isFbInterstitial", isFbInterstitial);
                    FastSave.getInstance().saveBoolean("isFbRewarded", isFbRewarded);
                    FastSave.getInstance().saveBoolean("isFbNativeAdvanced", isFbNativeAdvanced);
                    FastSave.getInstance().saveString("fbBANNER", fbBanner);
                    FastSave.getInstance().saveString("fbINTER", fbInterstitial);
                    FastSave.getInstance().saveString("fbNATIVE", fbNativeAdvanced);
                    FastSave.getInstance().saveString("fbReward", fbRewarded);

                    if (isUpdate && appVersion > BuildConfig.VERSION_CODE) {
                        New_Helper.New_open_Update_Dialog(NewSplashActivity.this);
                    } else {
                        if (FastSave.getInstance().getBoolean("SHOWADS", false)) {
                            if (FastSave.getInstance().getBoolean("isGoogleAppOpen", false)) {
                                New_loadApp_OpenAd();
                            } else {
                                nextActivity();
                            }
                        } else {
                            nextActivity();
                        }
                    }
                } catch (Exception e) {
                    Log.e("TAG", "showAdIfAvailable: 111 " + e.getMessage());
                    nextActivity();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("TAG1", error.getMessage());
                nextActivity();
            }
        });
    }


    private AppOpenAd New_appOpenAd;
    private AppOpenAd.AppOpenAdLoadCallback loadCallback;

    private void New_loadApp_OpenAd() {
        AdRequest request = new AdRequest.Builder().build();
        AppOpenAd.load(
                this,
                FastSave.getInstance().getString("APP_OPEN", ""),
                request,
                AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT,
                new AppOpenAd.AppOpenAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull AppOpenAd ad) {
                        New_appOpenAd = ad;
                        New_showApp_OpenAd();
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError error) {
                        Log.e("TAG", "onAdFailedToShowFullScreenContent: " + error.getMessage());
                        nextActivity();
                    }
                });
    }

    private void New_showApp_OpenAd() {
        if (New_appOpenAd != null) {
            FullScreenContentCallback fullScreenContentCallback =
                    new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            nextActivity();
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(AdError adError) {
                            Log.e("TAG", "onAdFailedToShowFullScreenContent: " + adError.getMessage());
                            nextActivity();
                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                        }
                    };

            New_appOpenAd.show(this);
            New_appOpenAd.setFullScreenContentCallback(fullScreenContentCallback);
        } else {
            nextActivity();
        }
    }



    public void attachBaseContext(Context context) {
        super.attachBaseContext(NewLanguageUtils.onAttach(context));
    }

    public void nextActivity() {
        Intent intent;
        if (NewFirstOpenUtils.getFirstOpenApp(this)) {
            intent = new Intent(this, NewHomeActivity.class);
            startActivity(intent);
        } else {
            startActivity(new Intent(this, NewLanguageFirstActivity.class));
        }
        finish();
    }

    public void onDestroy() {
        super.onDestroy();
    }
}

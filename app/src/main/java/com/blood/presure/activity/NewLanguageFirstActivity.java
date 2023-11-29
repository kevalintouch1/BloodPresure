package com.blood.presure.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.InputDeviceCompat;
import androidx.media3.common.util.UnstableApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appizona.yehiahd.fastsave.FastSave;
import com.blood.presure.Adapter.NewFirstLanguageAdapter;
import com.blood.presure.Utils.NewLanguageUtils;
import com.blood.presure.Utils.NewSaveLanguageUtils;
import com.blood.presure.Utils.NewFirstOpenUtils;
import com.blood.presure.chart.NewLanguageEntity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.blood.presure.R;


@UnstableApi
public class NewLanguageFirstActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView imgBackScreen;
    private ImageView imgSelected;
    private boolean isShowBack;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_languge_first);
        initViews();
        initEvent();

        LinearLayout adContainer = findViewById(R.id.adView);
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

    @SuppressLint("WrongConstant")
    public void initViews() {
        RecyclerView rvLanguage = findViewById(R.id.recyclerViewLanguage);
        this.imgSelected = findViewById(R.id.imgSelectLanguage);
        this.imgBackScreen = findViewById(R.id.img_exit_screen);
        if (getIntent() != null) {
            boolean booleanExtra = getIntent().getBooleanExtra("Change_Language", false);
            this.isShowBack = booleanExtra;
            Log.e("TAG", "initViews: "+booleanExtra );
            if (booleanExtra) {
                this.imgBackScreen.setVisibility(0);
            } else {
                this.imgBackScreen.setVisibility(8);
            }
        } else {
            this.imgBackScreen.setVisibility(8);
        }
        NewFirstOpenUtils.setFirstOpenApp(this, true);
        NewFirstLanguageAdapter newFirstLanguageAdapter2 = new NewFirstLanguageAdapter(this, new NewFirstLanguageAdapter.LanguageListener() {
            public void onLanguageChanged(NewLanguageEntity languageEntity, boolean z) {
                if (z) {
                    NewSaveLanguageUtils.saveLanguage("lang", "1", NewLanguageFirstActivity.this);
                    NewSaveLanguageUtils.saveLanguage("language", languageEntity.getCode(), NewLanguageFirstActivity.this);
                    NewSaveLanguageUtils.saveLanguage("languageTitle", languageEntity.getName(), NewLanguageFirstActivity.this);
                    NewLanguageUtils.setSelectedLanguage(NewLanguageFirstActivity.this, languageEntity);
                }
            }
        });
        rvLanguage.setAdapter(newFirstLanguageAdapter2);
        rvLanguage.setLayoutManager(new LinearLayoutManager(this));
        newFirstLanguageAdapter2.notifyDataSetChanged();
    }

    public void initEvent() {
        this.imgSelected.setOnClickListener(this);
        this.imgBackScreen.setOnClickListener(this);
    }

    public void onClick(View view) {
        if (view == this.imgSelected) {
            if (this.isShowBack) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        Intent intent = new Intent(NewLanguageFirstActivity.this, NewSplashActivity.class);
                        intent.addFlags(67108864);
                        NewLanguageFirstActivity.this.startActivity(intent);
                    }
                }, 800);
            } else {
                Intent intent = new Intent(this, NewOnBoardActivity.class);
                intent.addFlags(67108864);
                startActivity(intent);
            }
            finish();
        } else if (view == this.imgBackScreen) {
            finish();
        }
    }

    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        hideSystemUI();
    }

    private void hideSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(InputDeviceCompat.SOURCE_TOUCHSCREEN);
    }
}

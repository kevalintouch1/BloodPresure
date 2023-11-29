package com.blood.presure.activity;



import static com.blood.presure.ads.AdmobAdsHelper.ShowFullAds;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.InputDeviceCompat;
import androidx.media3.common.util.UnstableApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blood.presure.Adapter.NewFirstLanguageAdapter;
import com.blood.presure.R;
import com.blood.presure.Utils.NewFirstOpenUtils;
import com.blood.presure.Utils.NewLanguageUtils;
import com.blood.presure.Utils.NewSaveLanguageUtils;
import com.blood.presure.ads.AdmobAdsHelper;


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

        new AdmobAdsHelper(this).bannerAds(this,findViewById(R.id.adView));
        AdmobAdsHelper.LoadAdMobInterstitialAd(this);
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
        NewFirstLanguageAdapter newFirstLanguageAdapter2 = new NewFirstLanguageAdapter(this, (languageEntity, z) -> {
            if (z) {
                NewSaveLanguageUtils.saveLanguage("lang", "1", NewLanguageFirstActivity.this);
                NewSaveLanguageUtils.saveLanguage("language", languageEntity.getCode(), NewLanguageFirstActivity.this);
                NewSaveLanguageUtils.saveLanguage("languageTitle", languageEntity.getName(), NewLanguageFirstActivity.this);
                NewLanguageUtils.setSelectedLanguage(NewLanguageFirstActivity.this, languageEntity);
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
            NewFirstOpenUtils.setFirstOpenApp(this, true);
            if (this.isShowBack) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        Intent intent = new Intent(NewLanguageFirstActivity.this, NewSplashActivity.class);
                        intent.addFlags(67108864);
                        NewLanguageFirstActivity.this.startActivity(intent);
                        ShowFullAds(NewLanguageFirstActivity.this);
                    }
                }, 800);
            } else {
                Intent intent = new Intent(this, NewOnBoardActivity.class);
                intent.addFlags(67108864);
                startActivity(intent);
                ShowFullAds(NewLanguageFirstActivity.this);
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

package com.blood.presure.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.media3.common.util.UnstableApi;
import androidx.viewpager2.widget.ViewPager2;


import com.appizona.yehiahd.fastsave.FastSave;
import com.blood.presure.Adapter.NewViewPagerAdapter;
import com.blood.presure.Fragment.NewFragmentOnBoardOne;
import com.blood.presure.Utils.NewFirstOpenUtils;
import com.blood.presure.Fragment.NewFragmentOnBoardThree;
import com.blood.presure.Fragment.NewFragmentOnBoardTwo;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.blood.presure.R;

import java.util.ArrayList;

@UnstableApi public class NewOnBoardActivity extends AppCompatActivity implements View.OnClickListener {

    public boolean isStart = false;
    private ArrayList<Fragment> listFm;
    private LinearLayout lnNative;

    public TextView tvNext;
    private TextView tvSkip;
    private NewViewPagerAdapter newViewPagerAdapter;
    private ViewPager2 vpOnboard;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_on_board);
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

    public void initViews() {
        this.vpOnboard = (ViewPager2) findViewById(R.id.vp_onboard);
        this.tvNext = (TextView) findViewById(R.id.tv_next);
        this.tvSkip = (TextView) findViewById(R.id.tv_skip);
        NewFirstOpenUtils.setFirstOpenApp(this, true);
        ArrayList<Fragment> arrayList = new ArrayList<>();
        this.listFm = arrayList;
        arrayList.add(new NewFragmentOnBoardOne());
        this.listFm.add(new NewFragmentOnBoardTwo());
        this.listFm.add(new NewFragmentOnBoardThree());
        NewViewPagerAdapter newViewPagerAdapter2 = new NewViewPagerAdapter(this, this.listFm);
        this.newViewPagerAdapter = newViewPagerAdapter2;
        this.vpOnboard.setAdapter(newViewPagerAdapter2);
        this.vpOnboard.setOffscreenPageLimit(3);
        this.vpOnboard.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            public void onPageScrolled(int i, float f, int i2) {
                super.onPageScrolled(i, f, i2);
                if (i == 0) {
                    NewOnBoardActivity.this.tvNext.setText(NewOnBoardActivity.this.getResources().getString(R.string.next));
                    boolean unused = NewOnBoardActivity.this.isStart = false;
                } else if (i == 1) {
                    NewOnBoardActivity.this.tvNext.setText(NewOnBoardActivity.this.getResources().getString(R.string.next));
                    boolean unused2 = NewOnBoardActivity.this.isStart = false;
                } else if (i == 2) {
                    NewOnBoardActivity.this.tvNext.setText(NewOnBoardActivity.this.getResources().getString(R.string.start));
                    boolean unused3 = NewOnBoardActivity.this.isStart = true;
                }
            }

            public void onPageSelected(int i) {
                super.onPageSelected(i);
            }

            public void onPageScrollStateChanged(int i) {
                super.onPageScrollStateChanged(i);
            }
        });
    }

    public void initEvent() {
        this.tvNext.setOnClickListener(this);
        this.tvSkip.setOnClickListener(this);
    }

    public void onClick(View view) {
        if (view == this.tvNext) {
            if (this.isStart) {
                openMain();
            } else if (this.vpOnboard.getCurrentItem() == 0) {
                this.vpOnboard.setCurrentItem(1);
            } else {
                this.vpOnboard.setCurrentItem(2);
            }
        } else if (view == this.tvSkip) {
            openMain();
        }
    }

    private void openMain() {
        NewOnBoardActivity.this.startActivity(new Intent(NewOnBoardActivity.this, NewHomeActivity.class));
        NewOnBoardActivity.this.finish();
    }
}

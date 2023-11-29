package com.blood.presure.activity;



import static com.blood.presure.ads.AdmobAdsHelper.ShowFullAds;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.media3.common.util.UnstableApi;
import androidx.viewpager2.widget.ViewPager2;

import com.blood.presure.Adapter.NewViewPagerAdapter;
import com.blood.presure.Fragment.NewFragmentOnBoardFour;
import com.blood.presure.Fragment.NewFragmentOnBoardOne;
import com.blood.presure.Fragment.NewFragmentOnBoardThree;
import com.blood.presure.Fragment.NewFragmentOnBoardTwo;
import com.blood.presure.R;
import com.blood.presure.Utils.NewFirstOpenUtils;
import com.blood.presure.ads.AdmobAdsHelper;

import java.util.ArrayList;

@UnstableApi
public class NewOnBoardActivity extends AppCompatActivity implements View.OnClickListener {

    public boolean isStart = false;
    private LinearLayout lnNative;

    public TextView tvNext;
    private TextView tvSkip;
    private ViewPager2 vpOnboard;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_on_board);
        initViews();
        initEvent();

        new AdmobAdsHelper(this).bannerAds(this, findViewById(R.id.adView));
        AdmobAdsHelper.LoadAdMobInterstitialAd(this);
    }

    public void initViews() {
        this.vpOnboard = findViewById(R.id.vp_onboard);
        this.tvNext = findViewById(R.id.tv_next);
        this.tvSkip = findViewById(R.id.tv_skip);
        NewFirstOpenUtils.setFirstOpenApp(this, true);
        ArrayList<Fragment> arrayList = new ArrayList<>();
        arrayList.add(new NewFragmentOnBoardOne());
        arrayList.add(new NewFragmentOnBoardTwo());
        arrayList.add(new NewFragmentOnBoardThree());
        arrayList.add(new NewFragmentOnBoardFour());
        NewViewPagerAdapter newViewPagerAdapter2 = new NewViewPagerAdapter(this, arrayList);
        this.vpOnboard.setAdapter(newViewPagerAdapter2);
        this.vpOnboard.setOffscreenPageLimit(3);
        this.vpOnboard.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            public void onPageScrolled(int i, float f, int i2) {
                super.onPageScrolled(i, f, i2);
                if (i == 0) {
                    NewOnBoardActivity.this.tvNext.setText(NewOnBoardActivity.this.getResources().getString(R.string.next));
                    NewOnBoardActivity.this.isStart = false;
                } else if (i == 1) {
                    NewOnBoardActivity.this.tvNext.setText(NewOnBoardActivity.this.getResources().getString(R.string.next));
                    NewOnBoardActivity.this.isStart = false;
                } else if (i == 2) {
                    NewOnBoardActivity.this.tvNext.setText(NewOnBoardActivity.this.getResources().getString(R.string.next));
                    NewOnBoardActivity.this.isStart = false;
                } else if (i == 3) {
                    NewOnBoardActivity.this.tvNext.setText(NewOnBoardActivity.this.getResources().getString(R.string.start));
                    NewOnBoardActivity.this.isStart = true;
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
            } else if (this.vpOnboard.getCurrentItem() == 1) {
                this.vpOnboard.setCurrentItem(2);
            } else {
                this.vpOnboard.setCurrentItem(3);
            }
        } else if (view == this.tvSkip) {
            openMain();
        }
    }

    private void openMain() {
        NewOnBoardActivity.this.startActivity(new Intent(NewOnBoardActivity.this, NewHomeActivity.class));
        ShowFullAds(this);
        NewOnBoardActivity.this.finish();
    }
}

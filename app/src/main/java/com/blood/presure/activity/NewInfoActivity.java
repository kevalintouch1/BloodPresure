package com.blood.presure.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;

import com.appizona.yehiahd.fastsave.FastSave;
import com.blood.presure.Utils.NewSaveLanguageUtils;
import com.blood.presure.Utils.MeUtils;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.blood.presure.R;


public class NewInfoActivity extends NewBaseActivity {
    
    public TextView age;
    private TextView gender;
    
    public TextView height;
    private ImageView imgBack;
    private LinearLayout lnNative;
    
    public TextView weight;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_info);
        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                NewInfoActivity.this.next();
            }
        });
        this.imgBack = (ImageView) findViewById(R.id.img_back);
        this.age = (TextView) findViewById(R.id.ageT);
        this.weight = (TextView) findViewById(R.id.weightT);
        this.height = (TextView) findViewById(R.id.heightT);
        this.gender = (TextView) findViewById(R.id.genderT);
        TextView textView = this.age;
        textView.setText(MeUtils.getAge() + "");
        TextView textView2 = this.weight;
        textView2.setText(MeUtils.getWeight() + "");
        TextView textView3 = this.height;
        textView3.setText(MeUtils.getHeight() + "");
        this.gender.setText(MeUtils.getGenderText());
        findViewById(R.id.age).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                NewInfoActivity.this.showAgeDialog();
            }
        });
        findViewById(R.id.height).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                NewInfoActivity.this.showHeightDialog();
            }
        });
        findViewById(R.id.weight).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                NewInfoActivity.this.showWeightDialog();
            }
        });
        findViewById(R.id.gender).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                NewInfoActivity.this.changeGender();
            }
        });
        this.imgBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                NewInfoActivity.this.finish();
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


    public void next() {
        NewSaveLanguageUtils.saveLanguage("me", "1", NewInfoActivity.this);
        NewInfoActivity.this.startActivity(new Intent(NewInfoActivity.this, NewHeartRateActivityNew.class));
        NewInfoActivity.this.finish();
    }

    public void changeGender() {
        if (MeUtils.getGender() == 0) {
            MeUtils.setGender(1);
        } else {
            MeUtils.setGender(0);
        }
        this.gender.setText(MeUtils.getGenderText());
        this.gender.setAlpha(0.0f);
        this.gender.setScaleX(0.3f);
        this.gender.setScaleY(0.3f);
        this.gender.animate().alpha(1.0f).scaleY(1.0f).scaleX(1.0f).setDuration(300).start();
    }

    public void showAgeDialog() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this, com.google.android.material.R.style.Theme_Material3_DayNight_BottomSheetDialog);
        bottomSheetDialog.setContentView((int) R.layout.dialog_age_old);
        bottomSheetDialog.show();
        final NumberPicker numberPicker = (NumberPicker) bottomSheetDialog.findViewById(R.id.numberPicker);
        numberPicker.setMaxValue(110);
        numberPicker.setMinValue(5);
        MeUtils.setDividerColor(numberPicker, ContextCompat.getColor(this, R.color.light));
        numberPicker.setValue(MeUtils.getAge());
        bottomSheetDialog.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
                MeUtils.setAge(numberPicker.getValue());
                TextView access$000 = NewInfoActivity.this.age;
                access$000.setText(numberPicker.getValue() + "");
            }
        });
    }

    @SuppressLint("ResourceType")
    public void showHeightDialog() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this, com.google.android.material.R.style.Theme_Material3_DayNight_BottomSheetDialog);
        bottomSheetDialog.setContentView((int) R.layout.dialog_height);
        bottomSheetDialog.getWindow().setBackgroundDrawableResource(17170445);
        bottomSheetDialog.show();
        final NumberPicker numberPicker = (NumberPicker) bottomSheetDialog.findViewById(R.id.numberPicker);
        numberPicker.setMaxValue(ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION);
        numberPicker.setMinValue(50);
        MeUtils.setDividerColor(numberPicker, ContextCompat.getColor(this, R.color.light));
        numberPicker.setValue(MeUtils.getHeight());
        bottomSheetDialog.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
                MeUtils.setHeight(numberPicker.getValue());
                TextView access$100 = NewInfoActivity.this.height;
                access$100.setText(numberPicker.getValue() + "");
            }
        });
    }

    public void showWeightDialog() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this, com.google.android.material.R.style.Theme_Material3_DayNight_BottomSheetDialog);
        bottomSheetDialog.setContentView((int) R.layout.weight_dialog);
        bottomSheetDialog.show();
        final NumberPicker numberPicker = (NumberPicker) bottomSheetDialog.findViewById(R.id.numberPicker);
        numberPicker.setMaxValue(200);
        numberPicker.setMinValue(15);
        MeUtils.setDividerColor(numberPicker, ContextCompat.getColor(this, R.color.light));
        numberPicker.setValue(MeUtils.getWeight());
        bottomSheetDialog.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
                MeUtils.setWeight(numberPicker.getValue());
                TextView access$200 = NewInfoActivity.this.weight;
                access$200.setText(numberPicker.getValue() + "");
            }
        });
    }
}

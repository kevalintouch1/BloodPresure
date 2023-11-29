package com.blood.presure.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;

import com.blood.presure.R;
import com.blood.presure.Utils.MeUtils;
import com.blood.presure.Utils.NewSaveLanguageUtils;
import com.blood.presure.ads.AdmobAdsHelper;
import com.google.android.material.bottomsheet.BottomSheetDialog;


public class NewInfoActivity extends NewBaseActivity {

    public TextView age;
    private TextView gender;

    public TextView height;

    public TextView weight;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_info);
        findViewById(R.id.next).setOnClickListener(view -> NewInfoActivity.this.next());
        ImageView imgBack = findViewById(R.id.img_back);
        this.age = findViewById(R.id.ageT);
        this.weight = findViewById(R.id.weightT);
        this.height = findViewById(R.id.heightT);
        this.gender = findViewById(R.id.genderT);

        this.age.setText(MeUtils.getAge() + "");
        this.weight.setText(MeUtils.getWeight() + "");
        this.height.setText(MeUtils.getHeight() + "");
        this.gender.setText(MeUtils.getGenderText());

        new AdmobAdsHelper(this).bannerAds(this, findViewById(R.id.adView));
        findViewById(R.id.age).setOnClickListener(view -> NewInfoActivity.this.showAgeDialog());
        findViewById(R.id.height).setOnClickListener(view -> NewInfoActivity.this.showHeightDialog());
        findViewById(R.id.weight).setOnClickListener(view -> NewInfoActivity.this.showWeightDialog());
        findViewById(R.id.gender).setOnClickListener(view -> NewInfoActivity.this.changeGender());
        imgBack.setOnClickListener(view -> onBackPressed());
        AdmobAdsHelper.LoadAdMobInterstitialAd(this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        AdmobAdsHelper.ShowFullAds(this);
        finish();
    }

    public void next() {
        NewSaveLanguageUtils.saveLanguage("me", "1", NewInfoActivity.this);
        NewInfoActivity.this.startActivity(new Intent(NewInfoActivity.this, NewHeartRateActivityNew.class));
        AdmobAdsHelper.ShowFullAds(this);
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
        bottomSheetDialog.setContentView(R.layout.dialog_age_old);
        bottomSheetDialog.show();
        final NumberPicker numberPicker = bottomSheetDialog.findViewById(R.id.numberPicker);
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
        bottomSheetDialog.setContentView(R.layout.dialog_height);
        bottomSheetDialog.getWindow().setBackgroundDrawableResource(17170445);
        bottomSheetDialog.show();
        final NumberPicker numberPicker = bottomSheetDialog.findViewById(R.id.numberPicker);
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
        bottomSheetDialog.setContentView(R.layout.weight_dialog);
        bottomSheetDialog.show();
        final NumberPicker numberPicker = bottomSheetDialog.findViewById(R.id.numberPicker);
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

package com.blood.presure.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;

import com.blood.presure.R;
import com.blood.presure.Utils.MeUtils;
import com.blood.presure.activity.NewHeartRateActivityNew;
import com.blood.presure.activity.NewLanguageFirstActivity;
import com.blood.presure.activity.NewSplashActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;


public class NewSettingsFragment extends Fragment {
    
    public TextView age;
    private TextView gender;
    
    public TextView height;
    private LinearLayout lnMode;
    private View parent;
    
    public TextView weight;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.fragment_settings_heart, viewGroup, false);
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.parent = view;
        this.age = (TextView) view.findViewById(R.id.ageT);
        this.weight = (TextView) this.parent.findViewById(R.id.weightT);
        this.height = (TextView) this.parent.findViewById(R.id.heightT);
        this.gender = (TextView) this.parent.findViewById(R.id.genderT);
        this.lnMode = (LinearLayout) this.parent.findViewById(R.id.ln_mode);
        TextView textView = this.age;
        textView.setText(MeUtils.getAge() + "");
        TextView textView2 = this.weight;
        textView2.setText(MeUtils.getWeight() + "");
        TextView textView3 = this.height;
        textView3.setText(MeUtils.getHeight() + "");
        this.gender.setText(MeUtils.getGenderText());
        this.parent.findViewById(R.id.lang).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                @SuppressLint("UnsafeOptInUsageError") Intent intent = new Intent(NewSettingsFragment.this.getActivity(), NewLanguageFirstActivity.class);
                intent.putExtra("Change_Language", true);
                intent.addFlags(67108864);
                NewSettingsFragment.this.startActivity(intent);
            }
        });
        this.parent.findViewById(R.id.rateUs).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UnsafeOptInUsageError")
            public void onClick(View view) {
                if (NewHeartRateActivityNew.getInstance() != null) {
                    NewHeartRateActivityNew.getInstance().showRateUs();
                }
            }
        });
        this.parent.findViewById(R.id.age).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                NewSettingsFragment.this.showAgeDialog();
            }
        });
        this.parent.findViewById(R.id.height).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                NewSettingsFragment.this.showHeightDialog();
            }
        });
        this.parent.findViewById(R.id.weight).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                NewSettingsFragment.this.showWeightDialog();
            }
        });
        this.parent.findViewById(R.id.gender).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                NewSettingsFragment.this.changeGender();
            }
        });
        this.parent.findViewById(R.id.share).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.SEND");
                intent.putExtra("android.intent.extra.TEXT", NewSettingsFragment.this.getString(R.string.app_name) + " app => https://play.google.com/store/apps/details?id=" + NewSettingsFragment.this.getActivity().getPackageName());
                intent.setType("text/plain");
                NewSettingsFragment.this.startActivity(Intent.createChooser(intent, (CharSequence) null));
            }
        });
        this.lnMode.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                @SuppressLint("UnsafeOptInUsageError") Intent intent = new Intent(NewSettingsFragment.this.getActivity(), NewSplashActivity.class);
                intent.putExtra("Change_Language", true);
                intent.addFlags(67108864);
                NewSettingsFragment.this.startActivity(intent);
            }
        });
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


    public <T> T mo43984$(int i) {
        return (T) this.parent.findViewById(i);
    }

    public void showAgeDialog() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.Theme_Material3_DayNight_BottomSheetDialog);
        bottomSheetDialog.setContentView((int) R.layout.dialog_age_old);
        bottomSheetDialog.show();
        final NumberPicker numberPicker = (NumberPicker) bottomSheetDialog.findViewById(R.id.numberPicker);
        numberPicker.setMaxValue(110);
        numberPicker.setMinValue(5);
        MeUtils.setDividerColor(numberPicker, ContextCompat.getColor(getContext(), R.color.light));
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
                TextView access$000 = NewSettingsFragment.this.age;
                access$000.setText(numberPicker.getValue() + "");
            }
        });
    }

    public void showHeightDialog() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.Theme_Material3_DayNight_BottomSheetDialog);
        bottomSheetDialog.setContentView((int) R.layout.dialog_height);
        bottomSheetDialog.show();
        final NumberPicker numberPicker = (NumberPicker) bottomSheetDialog.findViewById(R.id.numberPicker);
        numberPicker.setMaxValue(ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION);
        numberPicker.setMinValue(50);
        MeUtils.setDividerColor(numberPicker, ContextCompat.getColor(getContext(), R.color.light));
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
                TextView access$100 = NewSettingsFragment.this.height;
                access$100.setText(numberPicker.getValue() + "");
            }
        });
    }

    public void showWeightDialog() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.Theme_Material3_DayNight_BottomSheetDialog);
        bottomSheetDialog.setContentView((int) R.layout.weight_dialog);
        bottomSheetDialog.show();
        final NumberPicker numberPicker = (NumberPicker) bottomSheetDialog.findViewById(R.id.numberPicker);
        numberPicker.setMaxValue(200);
        numberPicker.setMinValue(15);
        MeUtils.setDividerColor(numberPicker, ContextCompat.getColor(getContext(), R.color.light));
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
                TextView access$200 = NewSettingsFragment.this.weight;
                access$200.setText(numberPicker.getValue() + "");
            }
        });
    }
}

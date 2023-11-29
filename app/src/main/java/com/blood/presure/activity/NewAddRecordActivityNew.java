package com.blood.presure.activity;

import static com.blood.presure.ads.AdmobAdsHelper.LoadAdMobInterstitialAd;
import static com.blood.presure.ads.AdmobAdsHelper.ShowFullAds;


import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blood.presure.Fragment.NewDatePickerFragment;
import com.blood.presure.Fragment.NewTimePickerFragment;
import com.blood.presure.R;
import com.blood.presure.Utils.NewbpLevel;
import com.blood.presure.ads.AdmobAdsHelper;
import com.blood.presure.chart.NewrecordPressure;
import com.shawnlin.numberpicker.NumberPicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NewAddRecordActivityNew extends NewBaseActivity {
    public static NewrecordPressure edit;
    private TextView Diastoliclevel;
    private TextView DiastoliclevelConseil;
    private TextView DiastoliclevelDetails;
    public Date currentTimeDate;
    private TextView date;
    private NumberPicker dia;
    private List<NewbpLevel> list;
    private LinearLayout lnNativeBanner;
    private NumberPicker pulse;
    private NewbpLevel selectedLevel;
    private NumberPicker sys;
    private TextView time;

    public void onDestroy() {
        super.onDestroy();
        edit = null;
    }

    public void updateDateAndTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("hh:mm", Locale.getDefault());
        String format = simpleDateFormat.format(this.currentTimeDate);
        String format2 = simpleDateFormat2.format(this.currentTimeDate);
        this.date.setText(format);
        this.time.setText(format2);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getColor(R.color.white));
        setContentView(R.layout.new_add_record_activity);
        findViewById(R.id.save).setOnClickListener(view -> NewAddRecordActivityNew.this.save());
        findViewById(R.id.close).setOnClickListener(view -> NewAddRecordActivityNew.this.finish());
        TextView title = findViewById(R.id.title);
        this.date = findViewById(R.id.date);
        this.time = findViewById(R.id.time);
        this.currentTimeDate = Calendar.getInstance().getTime();
        updateDateAndTime();
        this.time.setOnClickListener(view -> new NewTimePickerFragment(NewAddRecordActivityNew.this.currentTimeDate, obj -> {
            NewAddRecordActivityNew.this.currentTimeDate = (Date) obj;
            NewAddRecordActivityNew.this.updateDateAndTime();
        }).show(NewAddRecordActivityNew.this.getSupportFragmentManager(), "timePicker"));
        this.date.setOnClickListener(view -> {
            try {
                new NewDatePickerFragment(NewAddRecordActivityNew.this.currentTimeDate, obj -> {
                    NewAddRecordActivityNew.this.currentTimeDate = (Date) obj;
                    NewAddRecordActivityNew.this.updateDateAndTime();
                }).show(NewAddRecordActivityNew.this.getSupportFragmentManager(), "datePicker");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        this.Diastoliclevel = findViewById(R.id.Diastoliclevel);
        this.DiastoliclevelConseil = findViewById(R.id.DiastoliclevelConseil);
        this.DiastoliclevelDetails = findViewById(R.id.DiastoliclevelDetails);
        this.sys = findViewById(R.id.sys);
        this.dia = findViewById(R.id.dia);
        this.pulse = findViewById(R.id.pulse);
        this.sys.setOnValueChangedListener((numberPicker, i, i2) -> NewAddRecordActivityNew.this.valueChanged());
        this.dia.setOnValueChangedListener((numberPicker, i, i2) -> NewAddRecordActivityNew.this.valueChanged());
        this.pulse.setOnValueChangedListener((numberPicker, i, i2) -> NewAddRecordActivityNew.this.valueChanged());
        List<NewbpLevel> list2 = NewbpLevel.getList(this);
        this.list = list2;
        this.selectedLevel = list2.get(1);
        this.list.get(0).view = findViewById(R.id.l1);
        this.list.get(1).view = findViewById(R.id.l2);
        this.list.get(2).view = findViewById(R.id.l3);
        this.list.get(3).view = findViewById(R.id.l4);
        this.list.get(4).view = findViewById(R.id.l5);
        this.list.get(5).view = findViewById(R.id.l6);
        for (int i = 0; i < this.list.size(); i++) {
            setupLavel(this.list.get(i));
        }
        updateLevels();
        NewrecordPressure recordpressure = edit;
        if (recordpressure != null) {
            this.sys.setValue(recordpressure.sys);
            this.dia.setValue(edit.dia);
            this.pulse.setValue(edit.pulse);
            this.currentTimeDate = new Date(edit.time);
            updateDateAndTime();
            NewbpLevel level = NewbpLevel.getLevel(edit.sys, edit.dia, this);
            this.selectedLevel = level;
            setupLavel(level);
            updateLevels();
            title.setText(R.string.edit);
            findViewById(R.id.delete).setVisibility(0);
        } else {
            findViewById(R.id.delete).setVisibility(8);
        }
        findViewById(R.id.delete).setOnClickListener(view -> {
            NewrecordPressure.delete(NewAddRecordActivityNew.edit);
            NewAddRecordActivityNew.this.finish();
        });

        new AdmobAdsHelper(this).bannerAds(this, findViewById(R.id.adView));
        LoadAdMobInterstitialAd(this);
    }


    public void save() {
        int value = this.dia.getValue();
        int value2 = this.sys.getValue();
        int value3 = this.pulse.getValue();
        long time2 = this.currentTimeDate.getTime();
        NewrecordPressure recordpressure = edit;
        if (recordpressure != null) {
            recordpressure.sys = value2;
            edit.dia = value;
            edit.pulse = value3;
            edit.time = time2;
            NewrecordPressure.save();
        } else {
            NewrecordPressure.AddRecord(new NewrecordPressure(value3, value, value2, time2));
            NewrecordPressure.save();
        }
        ShowFullAds(this);
        finish();
    }

    public void valueChanged() {
        this.selectedLevel = NewbpLevel.getLevel(this.sys.getValue(), this.dia.getValue(), this);
        updateLevels();
    }

    public void setupLavel(NewbpLevel bplevel) {
        bplevel.view.findViewById(R.id.color).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(bplevel.color)));
    }

    public void updateLevels() {
        for (int i = 0; i < this.list.size(); i++) {
            NewbpLevel bplevel = this.list.get(i);
            if (bplevel == this.selectedLevel) {
                bplevel.view.findViewById(R.id.bg).setVisibility(0);
                bplevel.view.findViewById(R.id.arrow).setVisibility(0);
                this.DiastoliclevelDetails.setText(bplevel.details);
                this.DiastoliclevelConseil.setText(bplevel.conseil);
                this.Diastoliclevel.setText(bplevel.title);
            } else {
                bplevel.view.findViewById(R.id.bg).setVisibility(4);
                bplevel.view.findViewById(R.id.arrow).setVisibility(4);
            }
        }
    }
}

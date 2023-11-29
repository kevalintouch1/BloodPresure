package com.blood.presure.activity;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appizona.yehiahd.fastsave.FastSave;
import com.blood.presure.Fragment.NewDatePickerFragment;
import com.blood.presure.Fragment.NewTimePickerFragment;
import com.blood.presure.Interface.simpleCallback;
import com.blood.presure.R;
import com.blood.presure.Utils.NewbpLevel;
import com.blood.presure.chart.NewrecordPressure;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
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
    private TextView title;

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
        setContentView((int) R.layout.new_add_record_activity);
        findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                NewAddRecordActivityNew.this.save();
            }
        });
        findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                NewAddRecordActivityNew.this.finish();
            }
        });
        this.title = (TextView) findViewById(R.id.title);
        this.date = (TextView) findViewById(R.id.date);
        this.time = (TextView) findViewById(R.id.time);
//        this.lnNativeBanner = (LinearLayout) findViewById(R.id.ln_banner);
//        BannerInApp.getInstance().showBanner(this, this.lnNativeBanner, FirebaseQuery.getIdBannerGA(this));
        this.currentTimeDate = Calendar.getInstance().getTime();
        updateDateAndTime();
        this.time.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                new NewTimePickerFragment(NewAddRecordActivityNew.this.currentTimeDate, new simpleCallback() {
                    public void callback(Object obj) {
                        Date unused = NewAddRecordActivityNew.this.currentTimeDate = (Date) obj;
                        NewAddRecordActivityNew.this.updateDateAndTime();
                    }
                }).show(NewAddRecordActivityNew.this.getSupportFragmentManager(), "timePicker");
            }
        });
        this.date.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    new NewDatePickerFragment(NewAddRecordActivityNew.this.currentTimeDate, new simpleCallback() {
                        public void callback(Object obj) {
                            Date unused = NewAddRecordActivityNew.this.currentTimeDate = (Date) obj;
                            NewAddRecordActivityNew.this.updateDateAndTime();
                        }
                    }).show(NewAddRecordActivityNew.this.getSupportFragmentManager(), "datePicker");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        this.Diastoliclevel = (TextView) findViewById(R.id.Diastoliclevel);
        this.DiastoliclevelConseil = (TextView) findViewById(R.id.DiastoliclevelConseil);
        this.DiastoliclevelDetails = (TextView) findViewById(R.id.DiastoliclevelDetails);
        this.sys = (NumberPicker) findViewById(R.id.sys);
        this.dia = (NumberPicker) findViewById(R.id.dia);
        this.pulse = (NumberPicker) findViewById(R.id.pulse);
        this.sys.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            public void onValueChange(NumberPicker numberPicker, int i, int i2) {
                NewAddRecordActivityNew.this.valueChanged();
            }
        });
        this.dia.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            public void onValueChange(NumberPicker numberPicker, int i, int i2) {
                NewAddRecordActivityNew.this.valueChanged();
            }
        });
        this.pulse.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            public void onValueChange(NumberPicker numberPicker, int i, int i2) {
                NewAddRecordActivityNew.this.valueChanged();
            }
        });
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
            this.title.setText(R.string.edit);
            findViewById(R.id.delete).setVisibility(0);
        } else {
            findViewById(R.id.delete).setVisibility(8);
        }
        findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                NewrecordPressure.delete(NewAddRecordActivityNew.edit);
                NewAddRecordActivityNew.this.finish();
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

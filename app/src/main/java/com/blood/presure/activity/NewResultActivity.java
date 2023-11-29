package com.blood.presure.activity;



import static com.blood.presure.ads.AdmobAdsHelper.ShowFullAds;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blood.presure.Adapter.NewArticlesAdapterThree;
import com.blood.presure.Measurement.DataCenter;
import com.blood.presure.Model.NewArticleModel;
import com.blood.presure.Model.NewCatModel;
import com.blood.presure.Model.NewRecordModel;
import com.blood.presure.Utils.NewSaveLanguageUtils;
import com.blood.presure.Utils.NewUtils;
import com.blood.presure.ads.AdmobAdsHelper;
import com.blood.presure.chart.NewChartView;
import com.blood.presure.helper.KnowledgeHelpers;
import com.blood.presure.Utils.MeUtils;
import com.blood.presure.Utils.NewUscreen;
import com.google.gson.Gson;
import com.blood.presure.R;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class NewResultActivity extends NewBaseActivity {
    List<NewRecordModel> newRecordModels;
    TextView bpm;
    View buttons;
    ImageView delete;
    boolean edit = false;
    TextView fast;
    View fastI;
    TextView normal;
    View normalI;
    NewRecordModel f10185r;
    TextView recheck;
    RecyclerView recyclerView;
    int result = 0;
    TextView resultText;
    TextView save;
    TextView slow;
    View slowI;
    TextView time;

    public interface UCallback {
        void callback(Object obj);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_result);
        NewSaveLanguageUtils.saveLanguage("resultOpens", (NewSaveLanguageUtils.LoadPref("resultOpens", this) + 1) + "", this);
        this.slow = findViewById(R.id.slow);
        this.normal = findViewById(R.id.normal);
        this.fast = findViewById(R.id.fast);
        this.buttons = findViewById(R.id.linearLayout);
        this.recyclerView = findViewById(R.id.recyclerView);
        this.resultText = findViewById(R.id.resultText);
        this.slowI = findViewById(R.id.slowIndicator);
        this.normalI = findViewById(R.id.normalIndicator);
        this.fastI = findViewById(R.id.fastIndicator);
        Gson gson = new Gson();
        if (getIntent().getExtras() == null) {
            this.f10185r = gson.fromJson(getIntent().getStringExtra("record"), NewRecordModel.class);
        } else if (getIntent().hasExtra("record")) {
            this.f10185r = gson.fromJson(getIntent().getStringExtra("record"), NewRecordModel.class);
        } else {
            try {
                this.newRecordModels = DataCenter.getRecords();
                this.f10185r = this.newRecordModels.get(getIntent().getExtras().getInt("pos"));
                this.edit = true;
                this.buttons.setVisibility(8);
            } catch (Exception unused) {
                finish();
                Toast.makeText(this, getString(R.string.no_record), 1).show();
                return;
            }
        }
        this.bpm = findViewById(R.id.bpm);
        this.time = findViewById(R.id.time);
        this.delete = findViewById(R.id.delete);
        TextView textView = this.bpm;
        textView.setText(this.f10185r.beat + "");
        this.time.setText(NewUtils.formatDate(this.f10185r.date, "MMMM dd,yyyy HH:mm"));
        TextView textView2 = findViewById(R.id.save);
        this.save = textView2;
        textView2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                NewResultActivity.this.save();
            }
        });
        TextView textView3 = findViewById(R.id.recheck);
        this.recheck = textView3;
        textView3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                NewResultActivity.this.recheck();
            }
        });
        if (this.edit) {
            this.recheck.setText("Cancel");
            this.delete.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    NewResultActivity.this.deleteRecord();
                }
            });
        } else {
            this.delete.setVisibility(8);
        }
        drawBeats();
        showResult();
        new AdmobAdsHelper(this).bannerAds(this, findViewById(R.id.adView));
        AdmobAdsHelper.LoadAdMobInterstitialAd(this);
    }

    private void showResult() {
        this.result = MeUtils.getHeartState(this.f10185r.beat, MeUtils.getAge(), this.f10185r.state);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        int i = this.result;
        if (i == -1) {
            showSlow();
        } else if (i == 0) {
            showNormal();
        } else if (i == 1) {
            showFast();
        }
    }

    private void showFast() {
        this.resultText.setText(getText(R.string.fast_heart_rate));
        this.normalI.setVisibility(4);
        this.slowI.setVisibility(4);
        this.fast.setY(-15.0f);
        this.slow.setAlpha(0.5f);
        this.normal.setAlpha(0.5f);
        showRecomanded("fast");
    }

    private void showNormal() {
        this.resultText.setText(getText(R.string.normal_heart_rate));
        this.fastI.setVisibility(4);
        this.slowI.setVisibility(4);
        this.normal.setY(-15.0f);
        this.slow.setAlpha(0.5f);
        this.fast.setAlpha(0.5f);
        showRecomanded("normal");
    }

    private void showSlow() {
        this.resultText.setText(getText(R.string.slow_heart_rate));
        this.fastI.setVisibility(4);
        this.normalI.setVisibility(4);
        this.slow.setY(-15.0f);
        this.fast.setAlpha(0.5f);
        this.normal.setAlpha(0.5f);
        showRecomanded("slow");
    }

    public void showRecomanded(String str) {
        String language = NewSaveLanguageUtils.getLanguage("languageTitle", this);
        List<NewCatModel> loadPagesParents = KnowledgeHelpers.loadPagesParents(language);
        if (loadPagesParents.size() > 1) {
            NewCatModel newCatModel = new NewCatModel("slow", loadPagesParents.get(1).path + "/" + str, language);
            newCatModel.loadPages();
            Collections.shuffle(newCatModel.pages);
            this.recyclerView.setAdapter(new NewArticlesAdapterThree(newCatModel.pages, obj -> {
                Intent intent = new Intent(NewResultActivity.this, NewWebViewActivity.class);
                intent.putExtra("article", new Gson().toJson(obj));
                NewResultActivity.this.startActivity(intent);
                ShowFullAds(NewResultActivity.this);
            }, this));
        }
    }

    public void deleteRecord() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.remove_dialog);
        dialog.show();
        NewUscreen.Init(this);
        dialog.findViewById(R.id.bg).getLayoutParams().width = (int) (((float) NewUscreen.width) * 0.9f);
        dialog.findViewById(R.id.cancel).setOnClickListener(view -> dialog.dismiss());
        dialog.findViewById(R.id.delete).setOnClickListener(view -> {
            NewResultActivity.this.newRecordModels.remove(NewResultActivity.this.f10185r);
            DataCenter.saveRecords(NewResultActivity.this.newRecordModels);
            NewResultActivity resultActivity = NewResultActivity.this;
            Toast.makeText(resultActivity, resultActivity.getString(R.string.record_deleted), 1).show();
            dialog.dismiss();
            NewResultActivity.this.finish();
        });
    }

    public void save() {
        if (this.edit) {
            DataCenter.saveRecords(this.newRecordModels);
            Toast.makeText(this, "Record updated", 1).show();
        } else {
            this.f10185r.age = MeUtils.getAge();
            DataCenter.AddRecord(this.f10185r);
            setResult(-1);
            Toast.makeText(this, getString(R.string.record_saved), 1).show();
        }
        finish();
    }

    public void recheck() {
        setResult(0);
        if (this.edit && DataCenter.getRecords() != null) {
            DataCenter.getRecords();
        }
        finish();
    }

    public void drawBeats() {
        NewChartView newChartView = findViewById(R.id.graphTextureView);
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < 6; i++) {
            int random = (int) ((Math.random() * 4.0d) + 10.0d);
            for (int i2 = 0; i2 < random; i2++) {
                arrayList.add(Float.valueOf((((float) Math.random()) * 0.09f) - 0.045f));
            }
            arrayList.add(Float.valueOf(0.8f));
            arrayList.add(Float.valueOf(-0.5f));
        }
        newChartView.data = arrayList;
        newChartView.invalidate();
    }

    @Override
    public void onBackPressed() {
        ShowFullAds(NewResultActivity.this);
        finish();
    }
}

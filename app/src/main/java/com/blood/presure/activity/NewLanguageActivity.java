package com.blood.presure.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blood.presure.Adapter.NewArticlesAdapterOne;
import com.blood.presure.Measurement.DownloadCommon;
import com.blood.presure.Adapter.NewLanguageAdapter;
import com.blood.presure.Model.NewLanguageModel;
import com.blood.presure.Utils.NewSaveLanguageUtils;
import com.blood.presure.R;

import java.util.ArrayList;
import java.util.List;


public class NewLanguageActivity extends NewBaseActivity {
    private List<NewLanguageModel> newLanguageModels = new ArrayList();
    private ImageView imgBack;
    private RecyclerView recyclerView;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_languages);
        this.newLanguageModels.add(new NewLanguageModel("English", "en", R.drawable.en));
        this.newLanguageModels.add(new NewLanguageModel("Français", "fr", R.drawable.fr));
        this.newLanguageModels.add(new NewLanguageModel("Arabic", "ar", R.drawable.ar));
        this.newLanguageModels.add(new NewLanguageModel("Spanish", "es", R.drawable.es));
        this.newLanguageModels.add(new NewLanguageModel("Hindi", "in", R.drawable.in));
        this.newLanguageModels.add(new NewLanguageModel("Russian", "ru", R.drawable.ru));
        this.newLanguageModels.add(new NewLanguageModel("Portuguese", "pt", R.drawable.pr));
        this.newLanguageModels.add(new NewLanguageModel("German", DownloadCommon.DOWNLOAD_REPORT_DOWNLOAD_ERROR, R.drawable.de));
        this.imgBack = (ImageView) findViewById(R.id.img_back_language);
        RecyclerView recyclerView2 = (RecyclerView) findViewById(R.id.recyclerView);
        this.recyclerView = recyclerView2;
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        this.recyclerView.setAdapter(new NewLanguageAdapter(this.newLanguageModels, new NewArticlesAdapterOne.simpleCallback() {
            public void callback(Object obj) {
                NewLanguageModel newLanguageModel = (NewLanguageModel) obj;
                NewSaveLanguageUtils.saveLanguage("lang", "1", NewLanguageActivity.this);
                NewSaveLanguageUtils.saveLanguage("language", newLanguageModel.code, NewLanguageActivity.this);
                NewSaveLanguageUtils.saveLanguage("languageTitle", newLanguageModel.name, NewLanguageActivity.this);
                if (NewSaveLanguageUtils.LoadPref("me", NewLanguageActivity.this) == 0) {
                    NewLanguageActivity.this.startActivity(new Intent(NewLanguageActivity.this, NewInfoActivity.class));
                } else {
                    NewLanguageActivity.this.startActivity(new Intent(NewLanguageActivity.this, NewSplashActivity.class));
                }
                NewLanguageActivity.this.finish();
            }
        }, this));
        this.imgBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                NewLanguageActivity.this.finish();
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
    }
}
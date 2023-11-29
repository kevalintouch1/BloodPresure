package com.blood.presure.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blood.presure.Adapter.NewRecordsAdapters;
import com.blood.presure.Interface.simpleCallback;
import com.blood.presure.chart.NewrecordPressure;
import com.blood.presure.R;

public class

NewAllRecordsActivityNew extends NewBaseActivity {
    private RecyclerView recyclerView;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.new_all_records_activity);
        RecyclerView recyclerView2 = (RecyclerView) findViewById(R.id.recyclerView);
        this.recyclerView = recyclerView2;
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                NewAllRecordsActivityNew.this.finish();
            }
        });
    }


    public void onResume() {
        super.onResume();
        this.recyclerView.setAdapter(new NewRecordsAdapters(NewrecordPressure.loadRecords(), new simpleCallback() {
            public void callback(Object obj) {
                NewAddRecordActivityNew.edit = (NewrecordPressure) obj;
                NewAllRecordsActivityNew.this.startActivity(new Intent(NewAllRecordsActivityNew.this, NewAddRecordActivityNew.class));
            }
        }, this));
    }

    public void changeStatusBarColor(int i) {
        Window window = getWindow();
        window.addFlags(Integer.MIN_VALUE);
        window.setStatusBarColor(ContextCompat.getColor(this, i));
    }
}

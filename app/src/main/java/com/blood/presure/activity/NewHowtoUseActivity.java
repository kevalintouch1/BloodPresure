package com.blood.presure.activity;

import android.os.Bundle;
import android.view.View;

import com.blood.presure.R;


public class NewHowtoUseActivity extends NewBaseActivity {

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_how_to_use);
        initView();
    }

    private void initView() {
        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                NewHowtoUseActivity.this.finish();
            }
        });
    }
}

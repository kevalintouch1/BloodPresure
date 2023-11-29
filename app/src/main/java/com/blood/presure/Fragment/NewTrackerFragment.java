package com.blood.presure.Fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;


import com.blood.presure.Measurement.DataCenter;
import com.blood.presure.Model.NewRecordModel;
import com.blood.presure.Adapter.NewPagerAdapter;
import com.google.firebase.messaging.ServiceStarter;
import com.blood.presure.R;


import java.util.List;

public class NewTrackerFragment extends Fragment {
    NewPagerAdapter adapter;
    TextView average;
    TextView max;
    TextView min;
    View parent;
    ViewPager viewPager;

    public static NewTrackerFragment newInstance() {
        return new NewTrackerFragment();
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.fragment_tracker_heart, viewGroup, false);
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.parent = view;
        this.viewPager = view.findViewById(R.id.viewPager);
        this.average = this.parent.findViewById(R.id.average);
        this.max = this.parent.findViewById(R.id.max);
        this.min = this.parent.findViewById(R.id.min);
    }

    public void updateData() {
        List<NewRecordModel> records = DataCenter.getRecords();
        if (records != null) {
            int i = 0;
            int i2 = 0;
            int i3 = ServiceStarter.ERROR_UNKNOWN;
            for (int i4 = 0; i4 < records.size(); i4++) {
                int i5 = records.get(i4).beat;
                if (i5 > i) {
                    i = i5;
                }
                if (i5 < i3) {
                    i3 = i5;
                }
                i2 += i5;
            }
            if (records.size() > 0) {
                int size = i2 / records.size();
                TextView textView = this.min;
                textView.setText(i3 + "");
                TextView textView2 = this.max;
                textView2.setText(i + "");
                TextView textView3 = this.average;
                textView3.setText(size + "");
            }
            NewPagerAdapter newPagerAdapter = new NewPagerAdapter(getChildFragmentManager());
            this.adapter = newPagerAdapter;
            this.viewPager.setAdapter(newPagerAdapter);
        }
    }

    public void setTint(View view, int i) {
        Drawable wrap = DrawableCompat.wrap(view.getBackground());
        DrawableCompat.setTint(wrap, i);
        view.setBackground(wrap);
    }

    public void onResume() {
        super.onResume();
        updateData();
    }
}

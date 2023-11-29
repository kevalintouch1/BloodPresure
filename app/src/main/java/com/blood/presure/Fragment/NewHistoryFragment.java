package com.blood.presure.Fragment;


import static com.blood.presure.ads.AdmobAdsHelper.ShowFullAds;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.blood.presure.Model.NewRecordModel;
import com.blood.presure.Adapter.NewRecordsAdapter;
import com.blood.presure.Measurement.DataCenter;

import com.blood.presure.activity.NewHeartRateActivityNew;
import com.blood.presure.activity.NewResultActivity;
import com.blood.presure.R;
import com.blood.presure.ads.AdmobAdsHelper;

import java.util.List;

public class NewHistoryFragment extends Fragment {
    NewRecordsAdapter adapter;
    View noData;
    View parent;
    RecyclerView recyclerView;

    public static NewHistoryFragment newInstance() {
        return new NewHistoryFragment();
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.fragment_history, viewGroup, false);
    }

    public void onViewCreated(@NonNull View view, Bundle bundle) {
        super.onViewCreated(view, bundle);

        AdmobAdsHelper.LoadAdMobInterstitialAd(requireActivity());

        this.parent = view;
        this.recyclerView = view.findViewById(R.id.recyclerView);
        this.noData = this.parent.findViewById(R.id.noData);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<NewRecordModel> records = DataCenter.getRecords();
        if (records.size() > 0) {
            this.recyclerView.setVisibility(0);
            this.noData.setVisibility(8);
            NewRecordsAdapter newRecordsAdapter = new NewRecordsAdapter(records, obj -> {
                Intent intent = new Intent(NewHistoryFragment.this.getActivity(), NewResultActivity.class);
                intent.putExtra("pos", ((Integer) obj).intValue());
                NewHistoryFragment.this.startActivity(intent);
                ShowFullAds(requireActivity());
            });
            this.adapter = newRecordsAdapter;
            this.recyclerView.setAdapter(newRecordsAdapter);
        } else {
            this.noData.setVisibility(0);
            this.recyclerView.setVisibility(8);
        }
        this.parent.findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (NewHeartRateActivityNew.getInstance() != null) {
                    NewHeartRateActivityNew.getInstance().showMeasure();
                }
            }
        });
    }
}

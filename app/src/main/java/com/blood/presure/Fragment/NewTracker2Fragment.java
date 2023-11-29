package com.blood.presure.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blood.presure.Adapter.NewRecordsAdapters;
import com.blood.presure.Interface.simpleCallback;
import com.blood.presure.Measurement.newDayAxisValueFormatter;
import com.blood.presure.R;
import com.blood.presure.activity.NewAddRecordActivityNew;
import com.blood.presure.activity.NewAllRecordsActivityNew;
import com.blood.presure.activity.NewSplashActivity;
import com.blood.presure.chart.NewCustomBarChartRender;
import com.blood.presure.chart.NewrecordPressure;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.messaging.ServiceStarter;

import java.util.ArrayList;
import java.util.List;

public class NewTracker2Fragment extends Fragment {
    private BarChart chart;
    private TextView dia;
    private View noData;
    private View parent;
    private TextView pulse;
    private RecyclerView recyclerView;
    private TextView sys;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.fragment_tracker_blood, viewGroup, false);
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.parent = view;
        this.recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        this.sys = (TextView) this.parent.findViewById(R.id.sys);
        this.dia = (TextView) this.parent.findViewById(R.id.dia);
        this.pulse = (TextView) this.parent.findViewById(R.id.pulse);
        this.noData = this.parent.findViewById(R.id.noData);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        updateStats();
        this.parent.findViewById(R.id.addRecord2).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                NewTracker2Fragment.this.addRecord2Clicked();
            }
        });
        this.parent.findViewById(R.id.addRecord).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                NewTracker2Fragment.this.addRecord2Clicked();
            }
        });
        this.parent.findViewById(R.id.img_mode).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                @SuppressLint("UnsafeOptInUsageError") Intent intent = new Intent(NewTracker2Fragment.this.getActivity(), NewSplashActivity.class);
                intent.putExtra("Change_Language", true);
                intent.addFlags(67108864);
                NewTracker2Fragment.this.startActivity(intent);
            }
        });
    }

    public void addRecord2Clicked() {
        NewTracker2Fragment.this.startActivity(new Intent(NewTracker2Fragment.this.getContext(), NewAddRecordActivityNew.class));
    }

    private void showList(List<NewrecordPressure> list) {
        this.recyclerView.setAdapter(new NewRecordsAdapters(list, new simpleCallback() {
            public void callback(Object obj) {
                if (obj == null) {
                    NewTracker2Fragment.this.startActivity(new Intent(NewTracker2Fragment.this.getActivity(), NewAllRecordsActivityNew.class));
                    return;
                }
                NewAddRecordActivityNew.edit = (NewrecordPressure) obj;
                NewTracker2Fragment.this.startActivity(new Intent(NewTracker2Fragment.this.getActivity(), NewAddRecordActivityNew.class));

            }
        }, getActivity()));
    }

    private void updateStats() {
        if (NewrecordPressure.loadRecords().size() == 0) {
            this.noData.setVisibility(0);
            this.parent.findViewById(R.id.addRecord).setVisibility(4);
            fillDummyData();
            showList(getDummyData());
            this.sys.setText("100");
            this.dia.setText("90");
            this.pulse.setText("83");
            return;
        }
        this.noData.setVisibility(8);
        this.parent.findViewById(R.id.addRecord).setVisibility(0);
        List<NewrecordPressure> loadRecords = NewrecordPressure.loadRecords();
        NewrecordPressure.invertSort();
        showChart(loadRecords);
        NewrecordPressure.sortRecords();
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < 3; i++) {
            if (NewrecordPressure.records.size() > i) {
                arrayList.add(NewrecordPressure.records.get(i));
            }
        }
        if (arrayList.size() == 3) {
            arrayList.add((Object) null);
        }
        showList(arrayList);
        if (arrayList.size() > 0) {
            NewrecordPressure recordpressure = (NewrecordPressure) arrayList.get(0);
            TextView textView = this.sys;
            textView.setText(recordpressure.sys + "");
            TextView textView2 = this.dia;
            textView2.setText(recordpressure.dia + "");
            TextView textView3 = this.pulse;
            textView3.setText(recordpressure.pulse + "");
        }
    }

    public List<NewrecordPressure> getDummyData() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new NewrecordPressure(80, 80, 100, System.currentTimeMillis()));
        arrayList.add(new NewrecordPressure(80, 70, 90, System.currentTimeMillis() - 86400000));
        arrayList.add(new NewrecordPressure(80, 80, 110, System.currentTimeMillis() - 172800000));
        return arrayList;
    }

    private void fillDummyData() {
        showChart(getDummyData());
    }

    public void showChart(List<NewrecordPressure> list) {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(list);
        BarChart barChart = (BarChart) this.parent.findViewById(R.id.chart1);
        this.chart = barChart;
        barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            public void onNothingSelected() {
            }

            public void onValueSelected(Entry entry, Highlight highlight) {
            }
        });
        this.chart.getDescription().setEnabled(false);
        this.chart.setMaxVisibleValueCount(10);
        this.chart.setCameraDistance(10.0f);
        this.chart.setPinchZoom(false);
        this.chart.setDrawBarShadow(false);
        this.chart.setDrawGridBackground(false);
        this.chart.setFitBars(true);
        this.chart.setDrawValueAboveBar(true);
        this.chart.setVisibleXRange(1.0f, 2.0f);
        this.chart.setExtraTopOffset(0.0f);
        this.chart.setExtraBottomOffset(0.0f);
        this.chart.setExtraLeftOffset(0.0f);
        this.chart.setExtraRightOffset(0.0f);
        XAxis xAxis = this.chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setTextColor(ContextCompat.getColor(getActivity(), R.color.accentColor));
        newDayAxisValueFormatter dayAxisValueFormatter = new newDayAxisValueFormatter(this.chart);
        xAxis.setGranularity(1.0f);
        xAxis.setLabelCount(7);
        xAxis.setValueFormatter(dayAxisValueFormatter);
        xAxis.setDrawAxisLine(false);
        dayAxisValueFormatter.setDate(arrayList);
        this.chart.getAxisLeft().setTextColor(ContextCompat.getColor(getActivity(), R.color.accentColor));
        this.chart.getAxisRight().setTextColor(ContextCompat.getColor(getActivity(), R.color.transparent));
        this.chart.getAxisLeft().setDrawGridLines(false);
        this.chart.getAxisRight().setDrawAxisLine(false);
        this.chart.getAxisLeft().setDrawAxisLine(false);
        this.chart.animateY(ServiceStarter.ERROR_UNKNOWN);
        Legend legend = this.chart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        legend.setTextColor(ContextCompat.getColor(getActivity(), R.color.accentColor));
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setFormSize(9.0f);
        legend.setTextSize(9.0f);
        legend.setXEntrySpace(2.0f);
        this.chart.getLegend().setEnabled(true);
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        for (int i = 0; i < arrayList.size(); i++) {
            int i2 = i * 2;
            arrayList2.add(new BarEntry((float) i2, (float) ((NewrecordPressure) arrayList.get(i)).sys));
            arrayList3.add(new BarEntry((float) (i2 + 1), (float) ((NewrecordPressure) arrayList.get(i)).dia));
        }
        BarDataSet barDataSet = new BarDataSet(arrayList2, getString(R.string.systolic));
        barDataSet.setBarBorderColor(0);
        barDataSet.setBarBorderWidth(5.0f);
        barDataSet.setColors(ColorTemplate.createColors(new int[]{ContextCompat.getColor(getActivity(), R.color.accentColor)}));
        barDataSet.setDrawValues(true);
        BarDataSet barDataSet2 = new BarDataSet(arrayList3, getString(R.string.diastolic));
        barDataSet2.setBarBorderColor(0);
        barDataSet2.setBarBorderWidth(5.0f);
        barDataSet2.setColors(ColorTemplate.createColors(new int[]{ContextCompat.getColor(getActivity(), R.color.accentColor2)}));
        barDataSet2.setDrawValues(true);
        BarChart barChart2 = this.chart;
        NewCustomBarChartRender newCustomBarChartRender = new NewCustomBarChartRender(barChart2, barChart2.getAnimator(), this.chart.getViewPortHandler());
        newCustomBarChartRender.setRadius(20);
        this.chart.setRenderer(newCustomBarChartRender);
        ArrayList arrayList4 = new ArrayList();
        arrayList4.add(barDataSet);
        arrayList4.add(barDataSet2);
        BarData barData = new BarData((List<IBarDataSet>) arrayList4);
        barData.setValueTextColor(-1);
        this.chart.setData(barData);
        this.chart.setVisibleXRangeMaximum(10.0f);
        this.chart.setVisibleXRangeMaximum(10.0f);
        this.chart.invalidate();
    }
}

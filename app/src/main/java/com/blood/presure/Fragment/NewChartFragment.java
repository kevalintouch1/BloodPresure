package com.blood.presure.Fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;


import com.blood.presure.Model.NewRecordModel;
import com.blood.presure.chart.NewCustomBarChartRender;
import com.blood.presure.Measurement.DataCenter;
import com.blood.presure.Measurement.DayAxisValueFormatter;
import com.blood.presure.Utils.MeUtils;

import com.blood.presure.Utils.NewUtils;
import com.blood.presure.activity.NewHeartRateActivityNew;
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
import com.blood.presure.R;

import java.util.ArrayList;
import java.util.List;

public class NewChartFragment extends Fragment {
    private List<NewRecordModel> newRecordModels;
    private TextView bpm;
    private TextView date;
    private TextView gender;
    private View indicator;
    private View noData;
    private View parent;
    private TextView result;
    private TextView state;

    private void updateStats() {
        List<NewRecordModel> list = this.newRecordModels;
        if (list != null) {
            showChart(list);
        }
    }

    public List<NewRecordModel> getDummyData() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new NewRecordModel(81, System.currentTimeMillis()));
        arrayList.add(new NewRecordModel(73, System.currentTimeMillis()));
        arrayList.add(new NewRecordModel(97, System.currentTimeMillis()));
        arrayList.add(new NewRecordModel(90, System.currentTimeMillis()));
        arrayList.add(new NewRecordModel(84, System.currentTimeMillis()));
        return arrayList;
    }

    @SuppressLint("WrongConstant")
    public void showChart(List<NewRecordModel> list) {
        ArrayList arrayList = new ArrayList();
        if (list.size() > 0) {
            arrayList.addAll(list);
            if (arrayList.size() == 1) {
                arrayList.add(0, new NewRecordModel(0, System.currentTimeMillis()));
                arrayList.add(0, new NewRecordModel(0, System.currentTimeMillis()));
                arrayList.add(0, new NewRecordModel(0, System.currentTimeMillis()));
                arrayList.add(new NewRecordModel(0, System.currentTimeMillis()));
                arrayList.add(new NewRecordModel(0, System.currentTimeMillis()));
                arrayList.add(new NewRecordModel(0, System.currentTimeMillis()));
            } else if (arrayList.size() == 2) {
                arrayList.add(0, new NewRecordModel(0, System.currentTimeMillis()));
                arrayList.add(0, new NewRecordModel(0, System.currentTimeMillis()));
                arrayList.add(new NewRecordModel(0, System.currentTimeMillis()));
                arrayList.add(new NewRecordModel(0, System.currentTimeMillis()));
            } else if (arrayList.size() == 3) {
                arrayList.add(0, new NewRecordModel(0, System.currentTimeMillis()));
                arrayList.add(0, new NewRecordModel(0, System.currentTimeMillis()));
                arrayList.add(new NewRecordModel(0, System.currentTimeMillis()));
                arrayList.add(new NewRecordModel(0, System.currentTimeMillis()));
            }
            this.noData.setVisibility(8);
        } else {
            arrayList.addAll(getDummyData());
            this.noData.setVisibility(0);
        }
        BarChart barChart = this.parent.findViewById(R.id.chart1);
        barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {

            }

            public void onNothingSelected() {
            }

        });
        barChart.getDescription().setEnabled(false);
        barChart.setMaxVisibleValueCount(10);
        barChart.setCameraDistance(10.0f);
        barChart.setPinchZoom(false);
        barChart.setDrawBarShadow(false);
        barChart.setDrawGridBackground(false);
        barChart.setFitBars(false);
        barChart.setVisibleXRange(5.0f, 9.0f);
        barChart.setExtraTopOffset(-30.0f);
        barChart.setExtraBottomOffset(10.0f);
        barChart.setExtraLeftOffset(15.0f);
        barChart.setExtraRightOffset(15.0f);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
        DayAxisValueFormatter dayAxisValueFormatter = new DayAxisValueFormatter(barChart);
        xAxis.setGranularity(1.0f);
        xAxis.setLabelCount(7);
        xAxis.setValueFormatter(dayAxisValueFormatter);
        dayAxisValueFormatter.setDate(arrayList);
        barChart.getAxisLeft().setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
        barChart.getAxisRight().setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.animateY(300);
        Legend legend = barChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        legend.setTextColor(ContextCompat.getColor(getActivity(), R.color.primary_light));
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setFormSize(9.0f);
        legend.setTextSize(11.0f);
        legend.setXEntrySpace(4.0f);
        barChart.getLegend().setEnabled(true);
        ArrayList arrayList2 = new ArrayList();
        for (int i = 0; i < arrayList.size(); i++) {
            arrayList2.add(new BarEntry((float) (i * 2), (float) ((NewRecordModel) arrayList.get(i)).beat));
        }
        BarDataSet barDataSet = new BarDataSet(arrayList2, "com/blood");
        barDataSet.setBarBorderColor(0);
        barDataSet.setBarBorderWidth(5.0f);
        barDataSet.setColors(ColorTemplate.createColors(new int[]{ContextCompat.getColor(getActivity(), R.color.primary_light)}));
        barDataSet.setDrawValues(true);
        BarChart barChart2 = barChart;
        NewCustomBarChartRender newCustomBarChartRender = new NewCustomBarChartRender(barChart2, barChart2.getAnimator(), barChart.getViewPortHandler());
        newCustomBarChartRender.setRadius(20);
        barChart.setRenderer(newCustomBarChartRender);
        ArrayList arrayList3 = new ArrayList();
        arrayList3.add(barDataSet);
        barChart.setData(new BarData((List<IBarDataSet>) arrayList3));
        barChart.setVisibleXRangeMaximum(15.0f);
        barChart.setVisibleXRangeMaximum(15.0f);
        barChart.invalidate();
    }

    public static NewChartFragment newInstance() {
        return new NewChartFragment();
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.fragment_chart, viewGroup, false);
    }

    @SuppressLint("WrongConstant")
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.parent = view;
        if (DataCenter.getRecords() != null) {
            this.newRecordModels = DataCenter.getRecords();
        }
        this.noData = this.parent.findViewById(R.id.noData);
        ViewPager viewPager = this.parent.findViewById(R.id.viewPager);
        updateStats();
        this.bpm = view.findViewById(R.id.bpm);
        this.result = view.findViewById(R.id.result);
        this.date = view.findViewById(R.id.date);
        this.state = view.findViewById(R.id.state);
        this.gender = view.findViewById(R.id.gender);
        this.indicator = view.findViewById(R.id.indicator);
        view.findViewById(R.id.arrow).setVisibility(4);
        showLastRecord();
        this.parent.findViewById(R.id.add).setOnClickListener(view1 -> NewHeartRateActivityNew.getInstance().showMeasure());
        this.parent.findViewById(R.id.add2).setOnClickListener(view12 -> NewHeartRateActivityNew.getInstance().showMeasure());
    }

    private void showLastRecord() {
        List<NewRecordModel> list = this.newRecordModels;
        if (list != null && list.size() != 0) {
            NewRecordModel newRecordModel = this.newRecordModels.get(0);
            this.result.setText(MeUtils.getHeartStateString(newRecordModel.beat, MeUtils.getAge(), newRecordModel.state));
            this.bpm.setText(newRecordModel.beat + "");
            this.state.setText(newRecordModel.getStateText());
            TextView textView2 = this.gender;
            textView2.setText(newRecordModel.getGenderText() + " | " + newRecordModel.age);
            this.date.setText(NewUtils.arabicToEnglishNumbers(NewUtils.formatDate(newRecordModel.date, "MMM dd, HH:mm")));
            Drawable wrap = DrawableCompat.wrap(this.indicator.getBackground());
            DrawableCompat.setTint(wrap, Color.parseColor(NewUtils.getBPMResultColor(MeUtils.getHeartState(newRecordModel.beat, MeUtils.getAge(), newRecordModel.state))));
            this.indicator.setBackground(wrap);
        }
    }
}

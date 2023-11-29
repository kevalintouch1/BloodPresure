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
    private BarChart chart;
    private TextView date;
    private TextView gender;
    private View indicator;
    private View noData;
    private View parent;
    private TextView result;
    private TextView state;
    private ViewPager viewPager;

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
        BarChart barChart = (BarChart) this.parent.findViewById(R.id.chart1);
        this.chart = barChart;
        barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {

            }

            public void onNothingSelected() {
            }

        });
        this.chart.getDescription().setEnabled(false);
        this.chart.setMaxVisibleValueCount(10);
        this.chart.setCameraDistance(10.0f);
        this.chart.setPinchZoom(false);
        this.chart.setDrawBarShadow(false);
        this.chart.setDrawGridBackground(false);
        this.chart.setFitBars(false);
        this.chart.setVisibleXRange(5.0f, 9.0f);
        this.chart.setExtraTopOffset(-30.0f);
        this.chart.setExtraBottomOffset(10.0f);
        this.chart.setExtraLeftOffset(15.0f);
        this.chart.setExtraRightOffset(15.0f);
        XAxis xAxis = this.chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
        DayAxisValueFormatter dayAxisValueFormatter = new DayAxisValueFormatter(this.chart);
        xAxis.setGranularity(1.0f);
        xAxis.setLabelCount(7);
        xAxis.setValueFormatter(dayAxisValueFormatter);
        dayAxisValueFormatter.setDate(arrayList);
        this.chart.getAxisLeft().setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
        this.chart.getAxisRight().setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
        this.chart.getAxisLeft().setDrawGridLines(false);
        this.chart.animateY(300);
        Legend legend = this.chart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        legend.setTextColor(ContextCompat.getColor(getActivity(), R.color.primary_light));
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setFormSize(9.0f);
        legend.setTextSize(11.0f);
        legend.setXEntrySpace(4.0f);
        this.chart.getLegend().setEnabled(true);
        ArrayList arrayList2 = new ArrayList();
        for (int i = 0; i < arrayList.size(); i++) {
            arrayList2.add(new BarEntry((float) (i * 2), (float) ((NewRecordModel) arrayList.get(i)).beat));
        }
        BarDataSet barDataSet = new BarDataSet(arrayList2, "com/blood");
        barDataSet.setBarBorderColor(0);
        barDataSet.setBarBorderWidth(5.0f);
        barDataSet.setColors(ColorTemplate.createColors(new int[]{ContextCompat.getColor(getActivity(), R.color.primary_light)}));
        barDataSet.setDrawValues(true);
        BarChart barChart2 = this.chart;
        NewCustomBarChartRender newCustomBarChartRender = new NewCustomBarChartRender(barChart2, barChart2.getAnimator(), this.chart.getViewPortHandler());
        newCustomBarChartRender.setRadius(20);
        this.chart.setRenderer(newCustomBarChartRender);
        ArrayList arrayList3 = new ArrayList();
        arrayList3.add(barDataSet);
        this.chart.setData(new BarData((List<IBarDataSet>) arrayList3));
        this.chart.setVisibleXRangeMaximum(15.0f);
        this.chart.setVisibleXRangeMaximum(15.0f);
        this.chart.invalidate();
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
        this.viewPager = (ViewPager) this.parent.findViewById(R.id.viewPager);
        updateStats();
        this.bpm = (TextView) view.findViewById(R.id.bpm);
        this.result = (TextView) view.findViewById(R.id.result);
        this.date = (TextView) view.findViewById(R.id.date);
        this.state = (TextView) view.findViewById(R.id.state);
        this.gender = (TextView) view.findViewById(R.id.gender);
        this.indicator = view.findViewById(R.id.indicator);
        view.findViewById(R.id.arrow).setVisibility(4);
        showLastRecord();
        this.parent.findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                NewHeartRateActivityNew.getInstance().showMeasure();
            }
        });
        this.parent.findViewById(R.id.add2).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                NewHeartRateActivityNew.getInstance().showMeasure();
            }
        });
    }

    private void showLastRecord() {
        List<NewRecordModel> list = this.newRecordModels;
        if (list != null && list.size() != 0) {
            NewRecordModel newRecordModel = this.newRecordModels.get(0);
            this.result.setText(MeUtils.getHeartStateString(newRecordModel.beat, MeUtils.getAge(), newRecordModel.state));
            TextView textView = this.bpm;
            textView.setText(newRecordModel.beat + "");
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

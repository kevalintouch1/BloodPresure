package com.blood.presure.Measurement;

import com.blood.presure.Model.NewRecordModel;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DayAxisValueFormatter extends ValueFormatter {
    List<NewRecordModel> newRecordModels = new ArrayList();
    private final BarLineChartBase<?> chart;
    private final String[] mMonths = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    private int determineYear(int i) {
        if (i <= 366) {
            return 2016;
        }
        if (i <= 730) {
            return 2017;
        }
        if (i <= 1094) {
            return 2018;
        }
        return i <= 1458 ? 2019 : 2020;
    }

    public DayAxisValueFormatter(BarLineChartBase<?> barLineChartBase) {
        this.chart = barLineChartBase;
    }

    public String getFormattedValue(float f) {
        int i;
        int i2 = (int) f;
        if (i2 == 0 || i2 == 1) {
            i = 0;
        } else {
            if (i2 % 2 != 0) {
                i2--;
            }
            i = i2 / 2;
        }
        return this.newRecordModels.size() > i ? getValueString(this.newRecordModels.get(i).date) : "";
    }

    public String getValueString(long j) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(new Date(j));
        int i = instance.get(5);
        instance.get(1);
        return i + "-" + (instance.get(2) + 1);
    }

    private int getDaysForMonth(int i, int i2) {
        throw new UnsupportedOperationException("Method not decompiled: com.HeartRate.utils.DayAxisValueFormatter.getDaysForMonth(int, int):int");
    }

    private int determineMonth(int i) {
        int i2 = -1;
        int i3 = 0;
        while (i3 < i) {
            i2++;
            if (i2 >= 12) {
                i2 = 0;
            }
            i3 += getDaysForMonth(i2, determineYear(i3));
        }
        return Math.max(i2, 0);
    }

    private int determineDayOfMonth(int i, int i2) {
        int i3 = 0;
        for (int i4 = 0; i4 < i2; i4++) {
            i3 += getDaysForMonth(i4 % 12, determineYear(i3));
        }
        return i - i3;
    }

    public void setDate(List<NewRecordModel> list) {
        this.newRecordModels = list;
    }
}

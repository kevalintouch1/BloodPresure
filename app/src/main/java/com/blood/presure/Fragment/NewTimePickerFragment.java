package com.blood.presure.Fragment;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import androidx.fragment.app.DialogFragment;


import com.blood.presure.Interface.simpleCallback;

import java.util.Calendar;
import java.util.Date;

public class NewTimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    private Calendar calendar;
    private simpleCallback callback;
    private Date date;

    public NewTimePickerFragment(Date date2, simpleCallback simplecallback) {
        this.date = date2;
        this.callback = simplecallback;
    }

    public Dialog onCreateDialog(Bundle bundle) {
        Calendar instance = Calendar.getInstance();
        this.calendar = instance;
        instance.setTime(this.date);
        return new TimePickerDialog(getActivity(), this, this.calendar.get(11), this.calendar.get(12), DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker timePicker, int i, int i2) {
        this.calendar.set(12, i2);
        this.calendar.set(10, i);
        simpleCallback simplecallback = this.callback;
        if (simplecallback != null) {
            simplecallback.callback(this.calendar.getTime());
        }
        this.callback = null;
    }
}

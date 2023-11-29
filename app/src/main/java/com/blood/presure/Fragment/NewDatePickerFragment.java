package com.blood.presure.Fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.fragment.app.DialogFragment;

import com.blood.presure.Interface.simpleCallback;

import java.util.Calendar;
import java.util.Date;

public class NewDatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private Calendar f10180c;
    private simpleCallback callback;
    private final Date date;

    public NewDatePickerFragment(Date date2, simpleCallback simplecallback) {
        this.date = date2;
        this.callback = simplecallback;
    }

    public Dialog onCreateDialog(Bundle bundle) {
        Calendar instance = Calendar.getInstance();
        this.f10180c = instance;
        instance.setTime(this.date);
        return new DatePickerDialog(getActivity(), this, this.f10180c.get(1), this.f10180c.get(2), this.f10180c.get(5));
    }

    public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
        this.f10180c.set(i, i2, i3);
        simpleCallback simplecallback = this.callback;
        if (simplecallback != null) {
            simplecallback.callback(this.f10180c.getTime());
        }
        this.callback = null;
    }
}

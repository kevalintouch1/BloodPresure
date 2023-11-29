package com.blood.presure.Adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.blood.presure.Utils.NewbpLevel;
import com.blood.presure.chart.NewrecordPressure;
import com.blood.presure.Interface.simpleCallback;
import com.blood.presure.R;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NewRecordsAdapters extends RecyclerView.Adapter<NewRecordsAdapters.MyViewHolder> {
    
    public simpleCallback callback;
    private final Context context;
    public List<NewrecordPressure> list;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout circleColor;
        TextView details;
        TextView dia;
        TextView level;
        View levelColor;
        View parent;
        TextView sys;

        public MyViewHolder(View view) {
            super(view);
            this.parent = view;
            this.level = view.findViewById(R.id.level);
            this.sys = view.findViewById(R.id.sys);
            this.dia = view.findViewById(R.id.dia);
            this.details = view.findViewById(R.id.details);
            this.levelColor = view.findViewById(R.id.levelColor);
            this.circleColor = view.findViewById(R.id.circleColor);
        }
    }

    public NewRecordsAdapters(List<NewrecordPressure> list2, simpleCallback simplecallback, Context context2) {
        this.list = list2;
        this.context = context2;
        this.callback = simplecallback;
    }

    public int getItemViewType(int i) {
        return this.list.get(i) == null ? 1 : 0;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view;
        if (i == 0) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.record_item_pressure, viewGroup, false);
        } else {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_more_records, viewGroup, false);
        }
        return new MyViewHolder(view);
    }

    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        final NewrecordPressure recordpressure = this.list.get(i);
        if (recordpressure != null) {
            NewbpLevel level = NewbpLevel.getLevel(recordpressure.sys, recordpressure.dia, this.context);
            myViewHolder.level.setText(level.title);
            TextView textView = myViewHolder.sys;
            textView.setText(recordpressure.sys + "");
            TextView textView2 = myViewHolder.dia;
            textView2.setText(recordpressure.dia + "");
            Date date = new Date(recordpressure.time);
            Calendar instance = Calendar.getInstance();
            instance.setTime(date);
            TextView textView3 = myViewHolder.details;
            textView3.setText((instance.get(2) + 1) + "-" + instance.get(5) + " , " + instance.get(11) + ":" + instance.get(12) + " , " + recordpressure.pulse + "com/blood");
            ViewCompat.setBackgroundTintList(myViewHolder.circleColor, ColorStateList.valueOf(Color.parseColor(level.color)));
            ViewCompat.setBackgroundTintList(myViewHolder.levelColor, ColorStateList.valueOf(Color.parseColor(level.color)));
        }
        myViewHolder.parent.setOnClickListener(view -> callback.callback(recordpressure));
    }

    public int getItemCount() {
        return this.list.size();
    }
}

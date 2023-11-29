package com.blood.presure.Adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.blood.presure.Model.NewRecordModel;
import com.blood.presure.Utils.NewUtils;
import com.blood.presure.Utils.MeUtils;
import com.blood.presure.R;

import java.util.List;

public class NewRecordsAdapter extends RecyclerView.Adapter<NewRecordsAdapter.MyViewHolder> {
    public ItemClicked clicked;
    public List<NewRecordModel> list;

    public interface ItemClicked {
        void onClick(Object obj);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public View arrow;
        public TextView bpm;
        public TextView date;
        public TextView gender;
        public View indicator;
        public View parent;
        public TextView result;
        public TextView state;

        public MyViewHolder(View view) {
            super(view);
            this.parent = view;
            this.bpm = (TextView) view.findViewById(R.id.bpm);
            this.result = (TextView) view.findViewById(R.id.result);
            this.date = (TextView) view.findViewById(R.id.date);
            this.state = (TextView) view.findViewById(R.id.state);
            this.gender = (TextView) view.findViewById(R.id.gender);
            this.indicator = view.findViewById(R.id.indicator);
        }
    }

    public NewRecordsAdapter(List<NewRecordModel> list2, ItemClicked itemClicked) {
        this.list = list2;
        this.clicked = itemClicked;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_record, viewGroup, false));
    }

    public void onBindViewHolder(MyViewHolder myViewHolder, @SuppressLint("RecyclerView") final int i) {
        NewRecordModel newRecordModel = this.list.get(i);
        myViewHolder.result.setText(MeUtils.getHeartStateString(newRecordModel.beat, MeUtils.getAge(), newRecordModel.state));
        TextView textView = myViewHolder.bpm;
        textView.setText(newRecordModel.beat + "");
        myViewHolder.state.setText(newRecordModel.getStateText());
        TextView textView2 = myViewHolder.gender;
        textView2.setText(newRecordModel.getGenderText() + " | " + newRecordModel.age);
        myViewHolder.date.setText(NewUtils.arabicToEnglishNumbers(NewUtils.formatDate(newRecordModel.date, "MMM dd, HH:mm")));
        Drawable wrap = DrawableCompat.wrap(myViewHolder.indicator.getBackground());
        DrawableCompat.setTint(wrap, Color.parseColor(NewUtils.getBPMResultColor(MeUtils.getHeartState(newRecordModel.beat, MeUtils.getAge(), newRecordModel.state))));
        myViewHolder.indicator.setBackground(wrap);
        myViewHolder.parent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (NewRecordsAdapter.this.clicked != null) {
                    NewRecordsAdapter.this.clicked.onClick(Integer.valueOf(i));
                }
            }
        });
    }

    public int getItemCount() {
        return this.list.size();
    }
}

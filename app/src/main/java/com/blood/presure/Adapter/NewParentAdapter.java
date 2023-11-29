package com.blood.presure.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.blood.presure.Model.NewKnowledgeModel;
import com.blood.presure.R;

import java.util.List;

public class NewParentAdapter extends RecyclerView.Adapter<NewParentAdapter.MyViewHolder> {
    private List<NewKnowledgeModel> list;
    private int selected = 0;

    public NewParentAdapter(List<NewKnowledgeModel> list2) {
        this.list = list2;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view;
        if (i == 0) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.k_parent_item, viewGroup, false);
        } else {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.k_parent_item_selected, viewGroup, false);
        }
        return new MyViewHolder(view);
    }

    public int getItemViewType(int i) {
        return i == this.selected ? 1 : 0;
    }

    public void onBindViewHolder(MyViewHolder myViewHolder, @SuppressLint("RecyclerView") final int i) {
        myViewHolder.title.setText(this.list.get(i).title);
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                NewParentAdapter.this.notifyItemChanged(i);
                NewParentAdapter.this.notifyItemChanged(i);
            }
        });
    }

    public int getItemCount() {
        return this.list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        public MyViewHolder(View view) {
            super(view);
            this.title = (TextView) view.findViewById(R.id.title);
        }
    }
}

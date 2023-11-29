package com.blood.presure.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.blood.presure.Model.NewLanguageModel;
import com.blood.presure.R;

import java.util.List;

public class NewLanguageAdapter extends RecyclerView.Adapter<NewLanguageAdapter.MyViewHolder> {
    public NewArticlesAdapterOne.simpleCallback callback;
    Context context;
    public List<NewLanguageModel> list;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView flag;
        View parent;
        TextView title;

        public MyViewHolder(View view) {
            super(view);
            this.parent = view;
            this.title = (TextView) view.findViewById(R.id.title);
            this.flag = (ImageView) view.findViewById(R.id.flag);
        }
    }

    public NewLanguageAdapter(List<NewLanguageModel> list2, NewArticlesAdapterOne.simpleCallback simplecallback, Context context2) {
        this.list = list2;
        this.context = context2;
        this.callback = simplecallback;
    }

    public int getItemViewType(int i) {
        return this.list.get(i) == null ? 1 : 0;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.language_item, viewGroup, false));
    }

    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        final NewLanguageModel newLanguageModel = this.list.get(i);
        myViewHolder.title.setText(newLanguageModel.name);
        myViewHolder.flag.setImageResource(newLanguageModel.flag);
        myViewHolder.parent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                NewLanguageAdapter.this.callback.callback(newLanguageModel);
            }
        });
    }

    public int getItemCount() {
        return this.list.size();
    }
}

package com.blood.presure.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.blood.presure.Model.NewArticleModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.blood.presure.R;

import java.util.Collections;
import java.util.List;

public class NewArticlesAdapterOne extends RecyclerView.Adapter<NewArticlesAdapterOne.MyViewHolder> {

    public simpleCallback callback;
    private final List<NewArticleModel> list;

    public interface simpleCallback {
        void callback(Object obj);
    }

    public int getItemViewType(int i) {
        return i;
    }

    public NewArticlesAdapterOne(List<NewArticleModel> list2, simpleCallback simplecallback) {
        this.list = list2;
        Collections.shuffle(list2);
        this.callback = simplecallback;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.article_item, viewGroup, false));
    }

    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        final NewArticleModel newArticleModel = this.list.get(i);
        myViewHolder.title.setText(newArticleModel.title);
        RequestManager with = Glide.with(myViewHolder.thumb);
        with.load("file:///android_asset/" + newArticleModel.thumb).into(myViewHolder.thumb);
        myViewHolder.parent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (NewArticlesAdapterOne.this.callback != null) {
                    NewArticlesAdapterOne.this.callback.callback(newArticleModel);
                }
            }
        });
    }

    public int getItemCount() {
        return this.list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        View f10182bg;
        View parent;
        ImageView thumb;
        TextView title;

        public MyViewHolder(View view) {
            super(view);
            this.parent = view;
            this.title = view.findViewById(R.id.title);
            this.thumb = view.findViewById(R.id.thumb);
            this.f10182bg = view.findViewById(R.id.bg);
        }
    }
}

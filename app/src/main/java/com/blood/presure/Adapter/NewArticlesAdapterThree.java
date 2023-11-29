package com.blood.presure.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.blood.presure.Model.NewArticleModel;
import com.blood.presure.R;

import java.util.ArrayList;
import java.util.List;

public class NewArticlesAdapterThree extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    simpleCallback callback;
    Context context;
    public List<NewArticleModel> list;
    int selected = 0;

    public interface simpleCallback {
        void callback(Object obj);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        View f10183bg;
        View parent;
        ImageView thumb;
        TextView title;

        public MyViewHolder(View view) {
            super(view);
            this.parent = view;
            this.title = view.findViewById(R.id.title);
            this.thumb = view.findViewById(R.id.thumb);
            this.f10183bg = view.findViewById(R.id.bg);
        }
    }

    public NewArticlesAdapterThree(List<NewArticleModel> list2, simpleCallback simplecallback, Context context2) {
        ArrayList arrayList = new ArrayList();
        this.list = arrayList;
        arrayList.addAll(list2);
        this.callback = simplecallback;
        this.context = context2;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.article_item3, viewGroup, false));
    }

    public int getItemViewType(int i) {
        return this.list.get(i) == null ? 2 : 1;
    }

    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        final NewArticleModel newArticleModel = this.list.get(i);
        MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        myViewHolder.title.setText(newArticleModel.title.replace("qst", "?"));
        RequestManager with = Glide.with(myViewHolder.thumb);
        with.load("file:///android_asset/" + newArticleModel.thumb).into(myViewHolder.thumb);
        myViewHolder.parent.setOnClickListener(view -> NewArticlesAdapterThree.this.callback.callback(newArticleModel));
    }

    public int getItemCount() {
        return this.list.size();
    }
}

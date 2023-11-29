package com.blood.presure.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import com.blood.presure.Utils.NewLanguageUtils;
import com.blood.presure.chart.NewLanguageEntity;
import com.blood.presure.R;

import java.util.ArrayList;

public class NewFirstLanguageAdapter extends RecyclerView.Adapter {

    public Activity mContext;

    public ArrayList<NewLanguageEntity> mLanguageEntities;

    public LanguageListener mLanguageListener;

    public interface LanguageListener {
        void onLanguageChanged(NewLanguageEntity newLanguageEntity, boolean z);
    }

    public NewFirstLanguageAdapter(Activity activity, LanguageListener languageListener) {
        ArrayList<NewLanguageEntity> arrayList = new ArrayList<>();
        this.mLanguageEntities = arrayList;
        arrayList.addAll(NewLanguageUtils.getSupportLanguageList());
        this.mContext = activity;
        this.mLanguageListener = languageListener;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ListFileViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_language_first, viewGroup, false));
    }

    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        bindView(viewHolder, i);
    }

    private void bindView(RecyclerView.ViewHolder viewHolder, final int i) {
        ListFileViewHolder listFileViewHolder = (ListFileViewHolder) viewHolder;
        NewLanguageEntity newLanguageEntity = this.mLanguageEntities.get(i);
        listFileViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                NewLanguageUtils.setPositionCountry(NewFirstLanguageAdapter.this.mContext, i);
                if (NewLanguageUtils.getSelectedLanguage(NewFirstLanguageAdapter.this.mContext).getCode().equals(((NewLanguageEntity) NewFirstLanguageAdapter.this.mLanguageEntities.get(i)).getCode())) {
                    NewFirstLanguageAdapter.this.mLanguageListener.onLanguageChanged((NewLanguageEntity) NewFirstLanguageAdapter.this.mLanguageEntities.get(i), false);
                } else {
                    NewFirstLanguageAdapter.this.mLanguageListener.onLanguageChanged((NewLanguageEntity) NewFirstLanguageAdapter.this.mLanguageEntities.get(i), true);
                }
                NewFirstLanguageAdapter.this.notifyDataSetChanged();
            }
        });
        if (NewLanguageUtils.getPositionCountry(this.mContext) == i) {
            listFileViewHolder.clItem.setBackgroundResource(R.drawable.bg_language_selected_first);
            listFileViewHolder.tvNameItemFile.setTextColor(this.mContext.getResources().getColor(R.color.primary_light));
            listFileViewHolder.iv_select.setImageResource(R.drawable.ic_country_selected);
        } else {
            listFileViewHolder.clItem.setBackgroundResource(R.drawable.bg_border_language);
            listFileViewHolder.tvNameItemFile.setTextColor(this.mContext.getResources().getColor(R.color.color_country_normal));
            listFileViewHolder.iv_select.setImageResource(R.drawable.ic_country_normal);
        }
        listFileViewHolder.tvNameItemFile.setText(newLanguageEntity.getName());
        listFileViewHolder.ivTypeItemFile.setImageResource(newLanguageEntity.getLinkImage());
    }

    public int getItemCount() {
        return this.mLanguageEntities.size();
    }

    class ListFileViewHolder extends RecyclerView.ViewHolder {
        public ConstraintLayout clItem;
        public ImageView ivTypeItemFile;
        public ImageView iv_select;
        public TextView tvNameItemFile;

        public ListFileViewHolder(View view) {
            super(view);
            this.clItem = (ConstraintLayout) view.findViewById(R.id.cl_item_language);
            this.ivTypeItemFile = (ImageView) view.findViewById(R.id.iv_type_item_file);
            this.tvNameItemFile = (TextView) view.findViewById(R.id.tv_name_item_file);
            this.iv_select = (ImageView) view.findViewById(R.id.iv_select);
        }
    }
}

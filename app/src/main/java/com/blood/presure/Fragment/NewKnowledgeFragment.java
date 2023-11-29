package com.blood.presure.Fragment;

import static com.blood.presure.ads.AdmobAdsHelper.LoadAdMobInterstitialAd;

import static com.blood.presure.ads.AdmobAdsHelper.ShowFullAds;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blood.presure.Adapter.NewParentAdapter;
import com.blood.presure.Measurement.MBridgeConstans;
import com.blood.presure.Model.NewArticleModel;
import com.blood.presure.Model.NewKnowledgeModel;
import com.blood.presure.R;
import com.blood.presure.Utils.NewSaveLanguageUtils;
import com.blood.presure.activity.NewWebViewActivity;
import com.blood.presure.helper.KnowledgeHelper;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import java.util.Collections;
import java.util.List;

public class NewKnowledgeFragment extends Fragment {
    private RecyclerView rvPageDefault;

    public interface simpleCallback {
        void callback(String str, String str2, String str3);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.fragment_knowledge_blood, viewGroup, false);
    }

    public void onViewCreated(@NonNull View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        RecyclerView k_parents_vp = view.findViewById(R.id.k_parents_vp);
        this.rvPageDefault = view.findViewById(R.id.k_pages);
        this.rvPageDefault.setLayoutManager(new LinearLayoutManager(getContext()));
        k_parents_vp.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        List<NewKnowledgeModel> loadPagesParents = KnowledgeHelper.loadPagesParents(NewSaveLanguageUtils.getLanguage("languageTitle", getActivity()));
        NewParentAdapter newParentAdapter = new NewParentAdapter(loadPagesParents);
        k_parents_vp.setAdapter(newParentAdapter);
        newParentAdapter.notifyDataSetChanged();
        if (loadPagesParents.size() > 0) {
            showPages(loadPagesParents);
        }
    }

    public void showPages(List<NewKnowledgeModel> list) {
        articles_adapter2 articles_adapter22 = new articles_adapter2(list, list.get(0).pages, new simpleCallback() {
            public void callback(String str, String str2, String str3) {
                NewKnowledgeFragment.this.showWebView(str, str2, str3);
            }
        });
        this.rvPageDefault.setAlpha(0.0f);
        this.rvPageDefault.setAdapter(articles_adapter22);
        this.rvPageDefault.animate().alpha(1.0f).setDuration(200);
    }

    @Override
    public void onResume() {
        super.onResume();
        LoadAdMobInterstitialAd(requireActivity());
    }

    public void showWebView(final String str, final String str2, final String str3) {
        Intent intent = new Intent(NewKnowledgeFragment.this.getActivity(), NewWebViewActivity.class);
        intent.putExtra(MBridgeConstans.DYNAMIC_VIEW_WX_PATH, str3);
        intent.putExtra("thumb", str2);
        intent.putExtra("title", str);
        NewKnowledgeFragment.this.startActivity(intent);
        ShowFullAds(requireActivity());
    }

    public class articles_adapter2 extends RecyclerView.Adapter<articles_adapter2.MyViewHolder> {
        public simpleCallback callback;
        public List<NewArticleModel> list;
        public List<NewKnowledgeModel> mListCat;

        public articles_adapter2(List<NewKnowledgeModel> list2, List<NewArticleModel> list3, simpleCallback simplecallback) {
            this.mListCat = list2;
            this.list = list3;
            Collections.shuffle(list3);
            this.callback = simplecallback;
        }

        public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.article_item2, viewGroup, false));
        }

        public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
            final NewArticleModel newArticleModel = this.list.get(i);
            final NewKnowledgeModel newKnowledgeModel = this.mListCat.get(i);
            myViewHolder.title.setText(newKnowledgeModel.title.replace("qst", "?"));
            RequestManager with = Glide.with(myViewHolder.thumb);
            with.load("file:///android_asset/" + newArticleModel.thumb).into(myViewHolder.thumb);
            myViewHolder.parent.setOnClickListener(view -> {
                if (articles_adapter2.this.callback != null) {
                    articles_adapter2.this.callback.callback(newKnowledgeModel.title, newArticleModel.thumb, newArticleModel.path);
                }
            });
        }

        public int getItemCount() {
            return this.list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            View f10181bg;
            View parent;
            ImageView thumb;
            TextView title;

            public MyViewHolder(View view) {
                super(view);
                this.parent = view;
                this.title = view.findViewById(R.id.title);
                this.thumb = view.findViewById(R.id.thumb);
                this.f10181bg = view.findViewById(R.id.bg);
            }
        }
    }
}

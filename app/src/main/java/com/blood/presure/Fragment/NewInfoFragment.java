package com.blood.presure.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appizona.yehiahd.fastsave.FastSave;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.blood.presure.Adapter.NewArticlesAdapterOne;
import com.blood.presure.Measurement.MBridgeConstans;
import com.blood.presure.Model.NewArticleModel;
import com.blood.presure.Model.NewCatModel;

import com.blood.presure.Utils.AdAdmob;
import com.blood.presure.Utils.NewEUGeneralHelper;
import com.blood.presure.Utils.NewSaveLanguageUtils;
import com.blood.presure.activity.NewWebViewActivity;
import com.blood.presure.helper.KnowledgeHelpers;
import com.google.gson.Gson;
import com.blood.presure.R;

import java.util.Collections;
import java.util.List;

public class NewInfoFragment extends Fragment {
    private NewArticlesAdapterOne NewArticlesAdapterOne;

    public simpleCallback callback;
    private RecyclerView recyclerView1;
    private RecyclerView recyclerView2;

    public interface simpleCallback {
        void callback(String str, String str2, String str3);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_info_heart, viewGroup, false);
        initViews(inflate);
        return inflate;
    }

    private void initViews(View view) {
        this.recyclerView1 = (RecyclerView) view.findViewById(R.id.articles1);
        this.recyclerView2 = (RecyclerView) view.findViewById(R.id.articles2);
        this.recyclerView1.setLayoutManager(new LinearLayoutManager(getContext(), 0, false));
        this.recyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));
        List<NewCatModel> loadPagesParents = KnowledgeHelpers.loadPagesParents(NewSaveLanguageUtils.getLanguage("languageTitle", getActivity()));
        if (loadPagesParents.size() > 0) {
            showArticlesTop(loadPagesParents.get(0));
            showArticlesMore(loadPagesParents);
        }
    }

    private void showArticlesTop(NewCatModel newCatModel) {
        this.NewArticlesAdapterOne = new NewArticlesAdapterOne(newCatModel.pages, new NewArticlesAdapterOne.simpleCallback() {
            public void callback(final Object obj) {
                Intent intent = new Intent(NewInfoFragment.this.getActivity(), NewWebViewActivity.class);
                intent.putExtra("article", new Gson().toJson((Object) (NewArticleModel) obj));
                NewInfoFragment.this.startActivity(intent);
            }
        });
        this.recyclerView1.setAlpha(0.0f);
        this.recyclerView1.setAdapter(this.NewArticlesAdapterOne);
        this.recyclerView1.animate().alpha(1.0f).setDuration(200);
    }

    private void showArticlesMore(List<NewCatModel> list) {
        CatAdapter catAdapter = new CatAdapter(list.get(0).pages, list, new simpleCallback() {
            public void callback(String str, String str2, String str3) {
                NewInfoFragment.this.openWebBM(str, str2, str3);
            }
        });
        this.recyclerView2.setAlpha(0.0f);
        this.recyclerView2.setAdapter(catAdapter);
        this.recyclerView2.animate().alpha(1.0f).setDuration(200);
    }

    @Override
    public void onResume() {
        super.onResume();
        new AdAdmob(getActivity()).LoadAdMobInterstitialAd(requireActivity());
    }

    public void openWebBM(final String str, final String str2, final String str3) {
        Intent intent = new Intent(NewInfoFragment.this.getActivity(), NewWebViewActivity.class);
        intent.putExtra(MBridgeConstans.DYNAMIC_VIEW_WX_PATH, str3);
        intent.putExtra("thumb", str2);
        intent.putExtra("title", str);
        NewInfoFragment.this.startActivity(intent);
        new AdAdmob(requireActivity()).ShowAds(requireActivity());
    }

    public class CatAdapter extends RecyclerView.Adapter<CatAdapter.MyViewHolder> {
        public List<NewArticleModel> list;
        public List<NewCatModel> mListNewCatModel;

        public CatAdapter(List<NewArticleModel> list2, List<NewCatModel> list3, simpleCallback simplecallback) {
            this.list = list2;
            this.mListNewCatModel = list3;
            Collections.shuffle(list2);
            simpleCallback unused = NewInfoFragment.this.callback = simplecallback;
        }

        public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.article_item2, viewGroup, false));
        }

        public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
            final NewArticleModel newArticleModel = this.list.get(i);
            final NewCatModel newCatModel = this.mListNewCatModel.get(i);
            myViewHolder.title.setText(newCatModel.title.replace("qst", "?"));
            RequestManager with = Glide.with((View) myViewHolder.thumb);
            with.load("file:///android_asset/" + newArticleModel.thumb).into(myViewHolder.thumb);
            myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if (NewInfoFragment.this.callback != null) {
                        NewInfoFragment.this.callback.callback(newCatModel.title, newArticleModel.thumb, newArticleModel.path);
                    }
                }
            });
        }

        public int getItemCount() {
            return this.list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {


            View f10186bg;
            ImageView thumb;
            TextView title;

            public MyViewHolder(View view) {
                super(view);
                this.title = (TextView) view.findViewById(R.id.title);
                this.thumb = (ImageView) view.findViewById(R.id.thumb);
                this.f10186bg = view.findViewById(R.id.bg);
            }
        }
    }
}

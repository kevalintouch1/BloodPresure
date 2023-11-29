package com.blood.presure.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.blood.presure.R;


public class NewFragmentOnBoardTwo extends Fragment {
    private ImageView mImgView;
    private TextView tvContent;
    private TextView tvTitle;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_onboard, viewGroup, false);
        iniViews(inflate);
        return inflate;
    }

    private void iniViews(View view) {
        ImageView imageView = (ImageView) view.findViewById(R.id.img_bg_onboard);
        this.mImgView = imageView;
        imageView.setImageResource(R.drawable.bg_onboard_2);
        this.tvTitle = (TextView) view.findViewById(R.id.tv_title);
        this.tvContent = (TextView) view.findViewById(R.id.tv_content);
        this.tvTitle.setText("Instant Heart Rate: HR Monitor");
        this.tvContent.setText("Most accurate and easy-to-use heart rate app");
    }
}

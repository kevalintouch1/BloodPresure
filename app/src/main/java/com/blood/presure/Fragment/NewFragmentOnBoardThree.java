package com.blood.presure.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.blood.presure.R;


public class NewFragmentOnBoardThree extends Fragment {

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_onboard, viewGroup, false);
        iniViews(inflate);
        return inflate;
    }

    private void iniViews(View view) {
        ImageView imageView = view.findViewById(R.id.img_bg_onboard);
        imageView.setImageResource(R.drawable.bg_onboard_3);
        TextView tvTitle = view.findViewById(R.id.tv_title);
        TextView tvContent = view.findViewById(R.id.tv_content);
        tvTitle.setText("Health Knowledge");
        tvContent.setText("Build a good habit and upgrade healthy knowledge");
    }
}

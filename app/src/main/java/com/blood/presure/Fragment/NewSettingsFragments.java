package com.blood.presure.Fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.appizona.yehiahd.fastsave.FastSave;
import com.blood.presure.R;
import com.blood.presure.Utils.NewUscreen;
import com.blood.presure.activity.NewLanguageFirstActivity;
import com.blood.presure.activity.NewSplashActivity;


public class NewSettingsFragments extends Fragment {
    private LinearLayout lnMode;
    View parent;
    TextView version;

    public void goToMyApp(boolean z) {
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.fragment_settings_blood, viewGroup, false);
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.parent = view;
        TextView textView = (TextView) view.findViewById(R.id.version);
        this.lnMode = (LinearLayout) view.findViewById(R.id.ln_mode);
        this.version = textView;
        textView.setText(getVersion());
        this.parent.findViewById(R.id.rateUs).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                NewSettingsFragments.this.showRateUs();
            }
        });
        this.parent.findViewById(R.id.privacy).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    NewSettingsFragments.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(FastSave.getInstance().getString("PRIVACY_POLICY",""))));
                } catch (ActivityNotFoundException unused) {
                    Toast.makeText(NewSettingsFragments.this.getContext(), "You don't have any app that can open this link", 0).show();
                }
            }
        });
        this.lnMode.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                @SuppressLint("UnsafeOptInUsageError") Intent intent = new Intent(NewSettingsFragments.this.getActivity(), NewSplashActivity.class);
                intent.putExtra("Change_Language", true);
                intent.addFlags(67108864);
                NewSettingsFragments.this.startActivity(intent);
            }
        });
        this.parent.findViewById(R.id.lang).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                @SuppressLint("UnsafeOptInUsageError") Intent intent = new Intent(NewSettingsFragments.this.getActivity(), NewLanguageFirstActivity.class);
                intent.putExtra("Change_Language", true);
                intent.addFlags(67108864);
                NewSettingsFragments.this.startActivity(intent);
            }
        });
    }

    private String getVersion() {
        try {
            String str = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0).versionName;
            return "Version " + str;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "...";
        }
    }

    @SuppressLint("ResourceType")
    public void showRateUs() {
        NewUscreen.Init(getActivity());
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_rate_app);
        dialog.getWindow().setBackgroundDrawableResource(17170445);
        dialog.show();
        dialog.findViewById(R.id.bg).getLayoutParams().width = (int) (((double) NewUscreen.width) * 0.9d);
        final RatingBar ratingBar = (RatingBar) dialog.findViewById(R.id.ratingBar);
        dialog.findViewById(R.id.rate).setEnabled(false);
        dialog.findViewById(R.id.rate).setAlpha(0.5f);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float f, boolean z) {
                if (f > 0.0f) {
                    dialog.findViewById(R.id.rate).setEnabled(true);
                    dialog.findViewById(R.id.rate).setAlpha(1.0f);
                    return;
                }
                dialog.findViewById(R.id.rate).setEnabled(false);
                dialog.findViewById(R.id.rate).setAlpha(0.5f);
            }
        });
        dialog.findViewById(R.id.rate).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (ratingBar.getRating() >= 4.0f) {
                    NewSettingsFragments.this.goToMyApp(true);
                } else {
                    Toast.makeText(NewSettingsFragments.this.getContext(), "Thanks for your rating.", 0).show();
                }
                dialog.dismiss();
            }
        });
    }


//    public <T> T mo43833$(int i) {
//        return this.parent.findViewById(i);
//    }
}

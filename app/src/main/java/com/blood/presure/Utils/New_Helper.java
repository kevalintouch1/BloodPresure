package com.blood.presure.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.TextView;

import com.blood.presure.R;

public class New_Helper {
    public static int New_show_Ads_NumberCount = 1;
    public static boolean new_is_show_open_ad = true;
    public static final String NEW_REMOVE_ADS_KEY = "REMOVE_ADS";
    public static final String NEW_REMOVE_ADS_PRODUCT_ID = "ad.free_remove.ads";
    public static final String NEW_SHOW_NON_PERSONALIZE_ADS_KEY = "SHOW_NON_PERSONALIZE_ADS";
    public static boolean new_is_come_from_splash = true;
    public static boolean new_is_come_from_start = true;

    public static void New_open_Update_Dialog(Context context) {
        Dialog dialog = new Dialog(context, R.style.CustomDialog);
        dialog.requestWindowFeature(1);
        dialog.setContentView(R.layout.new_update_dialog);
        dialog.findViewById(R.id.tv_maybe_later).setOnClickListener(view -> {
            ((Activity)context).finishAffinity();
            dialog.dismiss();
        });
        dialog.findViewById(R.id.tv_submit).setOnClickListener(view -> {
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + context.getPackageName()));
            intent.addFlags(1208483840);
            try {
                context.startActivity(intent);
            } catch (ActivityNotFoundException unused) {
            }
        });
        dialog.show();
    }

    public static void New_open_Exit_Dialog(Context context) {
        Dialog dialog = new Dialog(context, R.style.CustomDialog);
        dialog.requestWindowFeature(1);
        dialog.setContentView(R.layout.new_update_dialog);

        ((TextView)dialog.findViewById(R.id.tv_dialog_title)).setText("Exit");
        ((TextView)dialog.findViewById(R.id.tv_dialog_title_2)).setText("Are you sure, you want to exit?");
        ((TextView)dialog.findViewById(R.id.tv_submit)).setText("Exit");
        ((TextView)dialog.findViewById(R.id.tv_maybe_later)).setText("Cancel");

        dialog.findViewById(R.id.tv_maybe_later).setOnClickListener(view -> {
            dialog.dismiss();
        });
        dialog.findViewById(R.id.tv_submit).setOnClickListener(view -> {
            ((Activity)context).finishAffinity();
        });
        dialog.show();
    }

    public static void New_open_RateDialog(Context context) {
        Dialog dialog = new Dialog(context, R.style.CustomDialog);
        dialog.requestWindowFeature(1);
        dialog.setContentView(R.layout.new_update_dialog);
        dialog.setCancelable(false);

        ((TextView) dialog.findViewById(R.id.tv_dialog_title)).setText("Rate Us");
        ((TextView) dialog.findViewById(R.id.tv_dialog_title_2)).setText("Give us a quick rating so we know if you like it.");
        ((TextView) dialog.findViewById(R.id.tv_submit)).setText("Rate Now");
        ((TextView) dialog.findViewById(R.id.tv_maybe_later)).setText("Cancel");

        dialog.findViewById(R.id.tv_maybe_later).setOnClickListener(view -> dialog.dismiss());
        dialog.findViewById(R.id.tv_submit).setOnClickListener(view -> {
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + context.getPackageName()));
            intent.addFlags(1208483840);
            try {
                context.startActivity(intent);
            } catch (ActivityNotFoundException unused) {
            }
        });
        dialog.show();
    }

}

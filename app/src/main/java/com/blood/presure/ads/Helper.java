package com.blood.presure.ads;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.widget.TextView;

import com.blood.presure.R;

public class Helper {
    public static final String ADS_CONSENT_SET_KEY = "ADS_CONSENT_SET";
    public static final String EEA_USER_KEY = "EEA_USER";
    public static boolean is_show_open_ad = true;
    public static int showAdsNumberCount = 1;
    public static final String REMOVE_ADS_KEY = "REMOVE_ADS";
    public static final String REMOVE_ADS_PRODUCT_ID = "ad.free_remove.ads";

    public static boolean isOnline(Activity activity) {
        try {
            return ((ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo().isConnectedOrConnecting();
        } catch (Exception unused) {
            return false;
        }
    }

    public interface isMaybeLater {
        void isOk();
    }

    public static void openUpdateDialog(Context context, isMaybeLater isMaybeLater  ) {
        Dialog dialog = new Dialog(context, R.style.CustomDialog);
        dialog.requestWindowFeature(1);
        dialog.setContentView(R.layout.new_update_dialog);
        dialog.setCancelable(false);

        dialog.findViewById(R.id.tv_maybe_later).setOnClickListener(view -> {
            isMaybeLater.isOk();
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

    public static void openExitDialog(Context context) {
        Dialog dialog = new Dialog(context, R.style.CustomDialog);
        dialog.requestWindowFeature(1);
        dialog.setContentView(R.layout.new_update_dialog);

        try {
            ((TextView) dialog.findViewById(R.id.tv_dialog_title)).setText("Exit");
            ((TextView) dialog.findViewById(R.id.tv_dialog_title_2)).setText("Are you sure, you want to exit?");
            ((TextView) dialog.findViewById(R.id.tv_submit)).setText("Exit");
            ((TextView) dialog.findViewById(R.id.tv_maybe_later)).setText("Cancel");
        } catch (Exception e) {

        }

        dialog.findViewById(R.id.tv_maybe_later).setOnClickListener(view -> dialog.dismiss());
        dialog.findViewById(R.id.tv_submit).setOnClickListener(view -> ((Activity) context).finishAffinity());
        dialog.show();
    }
}

package com.blood.presure.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class NewPurchaseUtils {
    private static final String ID_REMOVE_ADS = "ID_REMOVE_ADS";
    private static final String KEY_REMOVE_ADS = "android.test.purchased";
    public static final String LICENSE_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhY0F2S+vnAoK1RzPBK8WCjQZgApXWx2QRMll2MXnyxk7b217jM8AwkevyIGuYkMz6rNbpBq1b1Fe2xaIn7aaL39II8+bMhy+dAwDsgBEV3Pal0kQZSVivKLA7JyWzybJ1f+z9kBMukpXqp+Q2Uixos9nzMR8hd8m0kCIeCIDLFe/lKC4zedPPFmUqwPVibDP/qGzI78z9Gmw5ZZhws4TAMgoeCvtTlIMatLK+c5RenN9kozP5aLt2JyWbx7HjJzNYwjNws33kS54xoMDm2tmpKdVFdXsK5BwgD+S+nrfwKHQMua5ymWik7BZBSFCwOBOXf7U53NrcH/bRDmSkk/NVQIDAQAB";
    private static final String PURCHASE_UTILS = "PURCHASE_UTILS";

    public static String getIdOneTime() {
        return "sub_one_time";
    }

    public static String getIdWeek() {
        return "sub_week";
    }

    public static String getPriceMonth() {
        return "sub_month";
    }

    public static String getPriceTrial() {
        return "sub_year_trial_3day";
    }

    public static boolean isNoAds(Context context) {
        return context.getSharedPreferences(PURCHASE_UTILS, 0).getBoolean(ID_REMOVE_ADS, false);
    }

    public static void setNoAds(Context context, boolean z) {
        SharedPreferences.Editor edit = context.getSharedPreferences(PURCHASE_UTILS, 0).edit();
        edit.putBoolean(ID_REMOVE_ADS, z);
        edit.apply();
    }
}

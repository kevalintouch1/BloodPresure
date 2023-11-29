package com.blood.presure.Utils;

import androidx.exifinterface.media.ExifInterface;

import com.blood.presure.Model.NewRecordModel;
import com.blood.presure.NewBloodApplication;
import com.blood.presure.Measurement.MBridgeConstans;
import com.blood.presure.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NewUtils {
    public static String formatDate(long j, String str) {
        String format = new SimpleDateFormat(str).format(new Date(j));
        return format.substring(0, 1).toUpperCase() + format.substring(1);
    }

    public static String getBPMResult(NewRecordModel newRecordModel) {
        NewBloodApplication instance = NewBloodApplication.getInstance();
        if (newRecordModel.state == 0) {
            if (newRecordModel.beat < 60) {
                return instance.getString(R.string.slow);
            }
            if (newRecordModel.beat <= 60 || newRecordModel.beat >= 100) {
                return instance.getString(R.string.Fast);
            }
            return instance.getString(R.string.Normal);
        } else if (newRecordModel.beat < 117) {
            return instance.getString(R.string.Warm_up);
        } else {
            if (newRecordModel.beat > 117 && newRecordModel.beat < 155) {
                return instance.getString(R.string.training);
            }
            if (newRecordModel.beat <= 156 || newRecordModel.beat >= 175) {
                return instance.getString(R.string.Extreme);
            }
            return instance.getString(R.string.High_intensity);
        }
    }

    public static String getBPMResultColor(int i) {
        NewBloodApplication.getInstance();
        if (i == -1) {
            return "#54A2FF";
        }
        return i == 0 ? "#20AA5B" : "#FF7564";
    }

    public static String arabicToEnglishNumbers(String str) {
        String str2 = "";
        for (char c : str.toCharArray()) {
            if (c != 1776) {
                switch (c) {
                    case 1633:
                        str2 = str2 + "1";
                        break;
                    case 1634:
                        str2 = str2 + "2";
                        break;
                    case 1635:
                        str2 = str2 + ExifInterface.GPS_MEASUREMENT_3D;
                        break;
                    case 1636:
                        str2 = str2 + "4";
                        break;
                    case 1637:
//                        str2 = str2 + CampaignEx.CLICKMODE_ON;
                        break;
                    case 1638:
                        str2 = str2 + "6";
                        break;
                    case 1639:
                        str2 = str2 + "7";
                        break;
                    case 1640:
                        str2 = str2 + "8";
                        break;
                    case 1641:
                        str2 = str2 + "9";
                        break;
                    default:
                        str2 = str2 + c;
                        break;
                }
            } else {
                str2 = str2 + MBridgeConstans.ENDCARD_URL_TYPE_PL;
            }
        }
        return str2;
    }
}

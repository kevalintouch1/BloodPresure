package com.blood.presure.Utils;

import android.content.Context;
import android.view.View;

import com.blood.presure.R;

import java.util.ArrayList;
import java.util.List;

public class NewbpLevel {
    static List<NewbpLevel> list;
    public String color;
    public String conseil;
    public String details;
    public String title;
    public View view;

    static boolean between(int i, int i2, int i3) {
        return i >= i2 && i <= i3;
    }

    public NewbpLevel(String str, String str2, String str3, String str4) {
        this.title = str;
        this.details = str3;
        this.conseil = str4;
        this.color = str2;
    }

    public static List<NewbpLevel> getList(Context context) {
        List<NewbpLevel> list2 = list;
        if (list2 != null) {
            return list2;
        }
        ArrayList arrayList = new ArrayList();
        list = arrayList;
        arrayList.add(new NewbpLevel(context.getString(R.string.hypertention), "#5692FF", "SYS < 90 or DIA <60", context.getString(R.string.hypertention_details)));
        list.add(new NewbpLevel(context.getString(R.string.normal), "#74C360", "SYS 90-119 and DIA 60-79", context.getString(R.string.normal_details)));
        list.add(new NewbpLevel(context.getString(R.string.elevated), "#FFE200", "SYS 120-129 and DIA 60-79", context.getString(R.string.elevated_details)));
        list.add(new NewbpLevel(context.getString(R.string.hypertention1), "#FFB200", "SYS 130-139 or DIA 80-89", context.getString(R.string.hypertention1_details)));
        list.add(new NewbpLevel(context.getString(R.string.hypertention2), "#FF824D", "SYS 140-180 or DIA 90-120", context.getString(R.string.hypertention2_details)));
        list.add(new NewbpLevel(context.getString(R.string.hypertensive), "#FF824D", "SYS > 180 or DIA > 120", context.getString(R.string.hypertensive_details)));
        return list;
    }

    public static NewbpLevel getLevel(int i, int i2, Context context) {
        List<NewbpLevel> list2 = getList(context);
        if (i > 180 || i2 > 120) {
            return list2.get(5);
        }
        if (between(i, 90, 119) && between(i2, 60, 79)) {
            return list2.get(1);
        }
        if (between(i, 120, 129) && between(i2, 60, 79)) {
            return list2.get(2);
        }
        if (i <= 90 || i2 < 60) {
            return list2.get(0);
        }
        if (between(i, 130, 139) || between(i2, 80, 89)) {
            return list2.get(3);
        }
        if (between(i, 140, 180) || between(i2, 90, 120)) {
            return list2.get(4);
        }
        return list2.get(1);
    }

    public static void clear() {
        list = null;
    }
}

package com.blood.presure.chart;

import androidx.core.app.NotificationCompat;

import com.blood.presure.NewBloodApplication;
import com.blood.presure.Utils.NewSaveLanguageUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NewrecordPressure implements Comparable<NewrecordPressure> {
    public static String RECORDS = "RECORDS";
    public static List<NewrecordPressure> records = new ArrayList();
    public int dia;
    public int pulse;
    public int sys;
    public long time;

    public NewrecordPressure(int i, int i2, int i3, long j) {
        this.pulse = i;
        this.dia = i2;
        this.sys = i3;
        this.time = j;
    }

    public static List<NewrecordPressure> loadRecords() {
        List<NewrecordPressure> list = records;
        if (list != null && list.size() > 0) {
            return records;
        }
        records = parse(NewSaveLanguageUtils.getLanguage(RECORDS, NewBloodApplication.getInstance()));
        sortRecords();
        return records;
    }

    public static void sortRecords() {
        Collections.sort(records);
    }

    public static void invertSort() {
        Collections.sort(records);
        Collections.reverse(records);
    }

    public static void AddRecord(NewrecordPressure recordpressure) {
        records.add(recordpressure);
        sortRecords();
        save();
    }

    public static void save() {
        try {
            JSONArray jSONArray = new JSONArray();
            for (int i = 0; i < records.size(); i++) {
                NewrecordPressure recordpressure = records.get(i);
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("pulse", recordpressure.pulse);
                jSONObject.put("dia", recordpressure.dia);
                jSONObject.put(NotificationCompat.CATEGORY_SYSTEM, recordpressure.sys);
                jSONObject.put("time", recordpressure.time);
                jSONArray.put(jSONObject);
            }
            NewSaveLanguageUtils.saveLanguage(RECORDS, jSONArray.toString(), NewBloodApplication.getInstance());
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public static List<NewrecordPressure> parse(String str) {
        ArrayList arrayList = new ArrayList();
        try {
            JSONArray jSONArray = new JSONArray(str);
            for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                arrayList.add(new NewrecordPressure(jSONObject.getInt("pulse"), jSONObject.getInt("dia"), jSONObject.getInt(NotificationCompat.CATEGORY_SYSTEM), jSONObject.getLong("time")));
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return arrayList;
    }

    public static void delete(NewrecordPressure recordpressure) {
        loadRecords();
        records.remove(recordpressure);
        save();
    }

    public int compareTo(NewrecordPressure recordpressure) {
        return (recordpressure == null || recordpressure.time >= this.time) ? 1 : -1;
    }
}

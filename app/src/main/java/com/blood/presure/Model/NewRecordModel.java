package com.blood.presure.Model;



import com.blood.presure.NewBloodApplication;
import com.blood.presure.R;


import org.json.JSONObject;

import java.util.Comparator;

public class NewRecordModel implements Comparator<NewRecordModel> {
    public static final Comparator<NewRecordModel> DESCENDING_COMPARATOR = new Comparator<NewRecordModel>() {
        public int compare(NewRecordModel newRecordModel, NewRecordModel newRecordModel2) {
            return Long.compare(newRecordModel2.date, newRecordModel.date);
        }
    };
    public int age;
    public int beat;
    public long date;
    public int gender;
    public String note;
    public int state;

    public int compare(NewRecordModel newRecordModel, NewRecordModel newRecordModel2) {
        return Long.compare(newRecordModel2.date, newRecordModel.date);
    }

    public NewRecordModel(int i, long j) {
        this.beat = i;
        this.date = j;
    }

    public String getJson() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("beat", this.beat);
            jSONObject.put("age", this.age);
            jSONObject.put("gender", this.gender);
            jSONObject.put("state", this.state);
            jSONObject.put("date", this.date);
            jSONObject.put("note", this.note);
            return jSONObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static NewRecordModel parse(JSONObject jSONObject) {
        try {
            int i = jSONObject.getInt("beat");
            int i2 = jSONObject.getInt("age");
            int i3 = jSONObject.getInt("gender");
            int i4 = jSONObject.getInt("state");
            long j = jSONObject.getLong("date");
            String string = jSONObject.getString("note");
            NewRecordModel newRecordModel = new NewRecordModel(i, j);
            newRecordModel.age = i2;
            newRecordModel.note = string;
            newRecordModel.gender = i3;
            newRecordModel.state = i4;
            return newRecordModel;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getStateText() {
        if (this.state == 0) {
            return NewBloodApplication.getInstance().getString(R.string.normal);
        }
        return NewBloodApplication.getInstance().getString(R.string.active);
    }

    public String getGenderText() {
        if (this.gender == 0) {
            return NewBloodApplication.getInstance().getString(R.string.Male);
        }
        return NewBloodApplication.getInstance().getString(R.string.Female);
    }
}

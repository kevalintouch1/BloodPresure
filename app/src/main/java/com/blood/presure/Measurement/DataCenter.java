package com.blood.presure.Measurement;

import com.blood.presure.Model.NewRecordModel;
import com.blood.presure.Utils.NewSaveLanguageUtils;
import com.blood.presure.NewBloodApplication;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataCenter {
    static List<NewRecordModel> newRecordModels = new ArrayList();

    public static void AddRecord(NewRecordModel newRecordModel) {
        getRecords().add(newRecordModel);
        saveRecords(newRecordModels);
    }

    public static void saveRecords(List<NewRecordModel> list) {
        NewSaveLanguageUtils.saveLanguage("records", new Gson().toJson((Object) list, new TypeToken<List<NewRecordModel>>() {
        }.getType()), NewBloodApplication.getInstance());
    }

    public static List<NewRecordModel> getRecords() {
        String language = NewSaveLanguageUtils.getLanguage("records", NewBloodApplication.getInstance());
        if (language.length() == 0) {
            ArrayList arrayList = new ArrayList();
            newRecordModels = arrayList;
            return arrayList;
        }
        List<NewRecordModel> list = (List) new Gson().fromJson(language, new TypeToken<List<NewRecordModel>>() {
        }.getType());
        newRecordModels = list;
        Collections.sort(list, NewRecordModel.DESCENDING_COMPARATOR);
        return newRecordModels;
    }
}

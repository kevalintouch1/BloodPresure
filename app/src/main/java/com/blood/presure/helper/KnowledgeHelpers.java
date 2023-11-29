package com.blood.presure.helper;

import com.blood.presure.Model.NewCatModel;
import com.blood.presure.NewBloodApplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KnowledgeHelpers {
    private static String[] list;

    public static List<NewCatModel> loadPagesParents(String str) {
        ArrayList arrayList = new ArrayList();
        try {
            String[] list2 = NewBloodApplication.getInstance().getAssets().list("pages/" + str + "/pages");
            list = list2;
            for (String catModel : list2) {
                NewCatModel newCatModel2 = new NewCatModel(catModel, "pages/" + str + "/pages", str);
                arrayList.add(newCatModel2);
                newCatModel2.loadPages();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arrayList;
    }
}

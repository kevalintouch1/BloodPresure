package com.blood.presure.helper;

import com.blood.presure.NewBloodApplication;
import com.blood.presure.Model.NewKnowledgeModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KnowledgeHelper {
    private static String[] list;

    public static List<NewKnowledgeModel> loadPagesParents(String str) {
        ArrayList arrayList = new ArrayList();
        try {
            String[] list2 = NewBloodApplication.getInstance().getAssets().list("blood_pressure_pages/" + str + "/pages");
            list = list2;
            for (String knowledgeModel : list2) {
                NewKnowledgeModel newKnowledgeModel2 = new NewKnowledgeModel(knowledgeModel, "blood_pressure_pages/" + str + "/pages", str);
                arrayList.add(newKnowledgeModel2);
                newKnowledgeModel2.loadPages();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arrayList;
    }
}

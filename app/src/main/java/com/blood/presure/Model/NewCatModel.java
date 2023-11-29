package com.blood.presure.Model;

import com.blood.presure.NewBloodApplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NewCatModel {
    public String lang;
    public List<NewArticleModel> pages = new ArrayList();
    public String path;
    public String title;

    public NewCatModel(String str, String str2, String str3) {
        this.title = str;
        this.path = str2;
        this.lang = str3;
    }

    public List<NewArticleModel> loadPages() {
        try {
            String[] list = NewBloodApplication.getInstance().getAssets().list(this.path);
            for (int i = 0; i < list.length; i++) {
                this.pages.add(new NewArticleModel(this.title, this.path + "/" + list[i], this.lang, this.path + "/" + list[i] + "/thumb.png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this.pages;
    }
}

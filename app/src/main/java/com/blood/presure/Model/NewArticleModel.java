package com.blood.presure.Model;

public class NewArticleModel {
    public String lang;
    public String path;
    public String thumb;
    public String title;

    public NewArticleModel(String str, String str2, String str3, String str4) {
        this.title = str;
        this.path = str2;
        this.lang = str3;
        this.thumb = str4;
    }
}

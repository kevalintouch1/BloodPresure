package com.blood.presure.chart;

public class NewLanguageEntity {
    private int linkImage;
    private String mAvatarCountry;
    private String mCode;
    private String mName;

    public NewLanguageEntity(String str, String str2, String str3, int i) {
        this.mName = str;
        this.mCode = str2;
        this.mAvatarCountry = str3;
        this.linkImage = i;
    }

    public String getName() {
        return this.mName;
    }

    public void setName(String str) {
        this.mName = str;
    }

    public String getCode() {
        return this.mCode;
    }

    public void setCode(String str) {
        this.mCode = str;
    }

    public String getmAvatarCountry() {
        return this.mAvatarCountry;
    }

    public void setAvatarCountry(String str) {
        this.mAvatarCountry = str;
    }

    public int getLinkImage() {
        return this.linkImage;
    }

    public void setLinkImage(int i) {
        this.linkImage = i;
    }
}

package com.example.liangwanandroid.model.home.bean;

import com.google.gson.annotations.SerializedName;

/***
 * 首页banner的item
 */
public class BannerDataItem {

    @SerializedName("desc")
    private String desc;

    @SerializedName("id")
    private int id;

    @SerializedName("imagePath")
    private String imagePath;

    @SerializedName("isVisible")
    private int isVisible;

    @SerializedName("order")
    private int order;

    @SerializedName("title")
    private String title;

    @SerializedName("type")
    private int type;

    @SerializedName("url")
    private String url;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(int isVisible) {
        this.isVisible = isVisible;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {

        return "";
    }
}

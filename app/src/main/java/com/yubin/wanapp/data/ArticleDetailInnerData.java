package com.yubin.wanapp.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * author : Yubin.Ying
 * time : 2018/11/2
 */
public class ArticleDetailInnerData {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("url")
    private String url;
}

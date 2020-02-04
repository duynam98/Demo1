package com.duynam.demo1.model;

import com.duynam.demo1.model.search.Urls;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class COmmonImage {
    @SerializedName("urls")
    @Expose
    private Urls urls;


    public Urls getUrls() {
        return urls;
    }

    public void setUrls(Urls urls) {
        this.urls = urls;
    }
}

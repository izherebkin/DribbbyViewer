package com.jetruby.dribbbyviewer.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Images extends RealmObject {
    @SerializedName("hidpi")
    private String hidpi;

    @SerializedName("normal")
    private String normal;

    @SerializedName("teaser")
    private String teaser;

    public Images() {
    }

    public String getHidpi() {
        return hidpi;
    }

    public void setHidpi(String hidpi) {
        this.hidpi = hidpi;
    }

    public String getNormal() {
        return normal;
    }

    public void setNormal(String normal) {
        this.normal = normal;
    }

    public String getTeaser() {
        return teaser;
    }

    public void setTeaser(String teaser) {
        this.teaser = teaser;
    }
}

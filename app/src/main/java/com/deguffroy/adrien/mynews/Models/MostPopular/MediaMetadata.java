package com.deguffroy.adrien.mynews.Models.MostPopular;

/**
 * Created by Adrien Deguffroy on 26/05/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MediaMetadata {
    @SerializedName("url")
    @Expose
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
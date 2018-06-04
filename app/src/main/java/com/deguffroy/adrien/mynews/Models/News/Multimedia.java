package com.deguffroy.adrien.mynews.Models.News;

/**
 * Created by Adrien Deguffroy on 31/05/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Multimedia {
    @SerializedName("url")
    @Expose
    private String url;

    public String getUrl() {
        return url;
    }
}

package com.deguffroy.adrien.mynews.Models.TopStories;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Adrien Deguffroy on 21/05/2018.
 */

public class Multimedia {

    @SerializedName("url")
    @Expose
    private String url;

    public String getUrl() {
        return url;
    }
}

package com.deguffroy.adrien.mynews.Models.Search;

/**
 * Created by Adrien Deguffroy on 31/05/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseMultimedia {
    @SerializedName("url")
    @Expose
    private String url;

    public ResponseMultimedia(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}

package com.deguffroy.adrien.mynews.Models.Search;

/**
 * Created by Adrien Deguffroy on 30/05/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Headline {
    @SerializedName("main")
    @Expose
    private String main;

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }
}

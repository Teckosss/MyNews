package com.deguffroy.adrien.mynews.Models.Search;

/**
 * Created by Adrien Deguffroy on 31/05/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Response {
    @SerializedName("docs")
    @Expose
    private final List<Doc> docs = null;

    public List<Doc> getDocs() {
        return docs;
    }
}

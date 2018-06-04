package com.deguffroy.adrien.mynews.Models;

/**
 * Created by Adrien Deguffroy on 31/05/2018.
 */

import com.deguffroy.adrien.mynews.Models.News.Result;
import com.deguffroy.adrien.mynews.Models.Search.Response;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NYTimesResultAPI {
    @SerializedName("results")
    @Expose
    private final List<Result> results = null;

    public List<Result> getResults() {
        return results;
    }

    //FOR SEARCH ARTICLES API
    @SerializedName("response")
    @Expose
    private Response response;

    public Response getResponse() {
        return response;
    }
}

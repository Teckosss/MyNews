package com.deguffroy.adrien.mynews.Models.TopStories;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Adrien Deguffroy on 21/05/2018.
 */

public class TopStoriesNews {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("results")
    @Expose
    private List<ResultTopStories> results = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ResultTopStories> getResults() {
        return results;
    }

    public void setResults(List<ResultTopStories> results) {
        this.results = results;
    }
}
package com.deguffroy.adrien.mynews.Models.MostPopular;

/**
 * Created by Adrien Deguffroy on 26/05/2018.
 */

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MostPopularNews {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("results")
    @Expose
    private List<ResultMostPopular> results = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ResultMostPopular> getResults() {
        return results;
    }

    public void setResults(List<ResultMostPopular> results) {
        this.results = results;
    }

}
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
    @SerializedName("copyright")
    @Expose
    private String copyright;
    @SerializedName("section")
    @Expose
    private String section;
    @SerializedName("last_updated")
    @Expose
    private String lastUpdated;
    @SerializedName("num_results")
    @Expose
    private Integer numResults;
    @SerializedName("results")
    @Expose
    private List<ResultTopStories> results = null;

    public TopStoriesNews(String status, String copyright, String section, String lastUpdated, Integer numResults, List<ResultTopStories> results) {
        this.status = status;
        this.copyright = copyright;
        this.section = section;
        this.lastUpdated = lastUpdated;
        this.numResults = numResults;
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Integer getNumResults() {
        return numResults;
    }

    public void setNumResults(Integer numResults) {
        this.numResults = numResults;
    }

    public List<ResultTopStories> getResults() {
        return results;
    }

    public void setResults(List<ResultTopStories> results) {
        this.results = results;
    }
}
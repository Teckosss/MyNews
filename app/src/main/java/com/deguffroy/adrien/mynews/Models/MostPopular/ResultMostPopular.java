package com.deguffroy.adrien.mynews.Models.MostPopular;

/**
 * Created by Adrien Deguffroy on 26/05/2018.
 */

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultMostPopular {

    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("section")
    @Expose
    private String section;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("published_date")
    @Expose
    private String publishedDate;
    @SerializedName("media")
    @Expose
    private List<Media> media = null;

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getSection() {
        return section;
    }
    public void setSection(String section) {
        this.section = section;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getPublishedDate() {
        return publishedDate;
    }
    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }
    public List<Media> getMedia() {
        return media;
    }
    public void setMedia(List<Media> media) {
        this.media = media;
    }
}

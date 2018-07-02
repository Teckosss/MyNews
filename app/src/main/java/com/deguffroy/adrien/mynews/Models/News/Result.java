package com.deguffroy.adrien.mynews.Models.News;

/**
 * Created by Adrien Deguffroy on 31/05/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Result {
    @SerializedName("section")
    @Expose
    private String section;
    @SerializedName("subsection")
    @Expose
    private String subsection;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("published_date")
    @Expose
    private String publishedDate;

    //FOR TOPSTORIES IMG URL
    @SerializedName("multimedia")
    @Expose
    private final List<Multimedia> multimedia = null;

    //FOR MOSTPOPULAR IMG URL

    private String urlImageMedia = null;

    public Result(String section, String subsection, String title, String url, String publishedDate, String urlImageMedia) {
        this.section = section;
        this.subsection = subsection;
        this.title = title;
        this.url = url;
        this.publishedDate = publishedDate;
        this.urlImageMedia = urlImageMedia;
    }

    public String getSection() {
        return section;
    }

    public String getSubsection() {
        return subsection;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public String setUrlImageMedia(String media){
        return this.urlImageMedia = media;
    }

    public String getUrlImageMedia() {
        return urlImageMedia;
    }

    public List<Multimedia> getMultimedia() {
        return multimedia;
    }
}

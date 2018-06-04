package com.deguffroy.adrien.mynews.Models.Search;

/**
 * Created by Adrien Deguffroy on 30/05/2018.
 */

import java.util.List;

import com.deguffroy.adrien.mynews.Models.TopStories.Multimedia;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Doc {
    @SerializedName("web_url")
    @Expose
    private String webUrl;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("multimedia")
    @Expose
    private List<Multimedia> multimedia = null;
    @SerializedName("headline")
    @Expose
    private Headline headline;
    @SerializedName("pub_date")
    @Expose
    private String pubDate;
    @SerializedName("type_of_material")
    @Expose
    private String typeOfMaterial;

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public List<Multimedia> getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(List<Multimedia> multimedia) {
        this.multimedia = multimedia;
    }

    public Headline getHeadline() {
        return headline;
    }

    public void setHeadline(Headline headline) {
        this.headline = headline;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getTypeOfMaterial() {
        return typeOfMaterial;
    }

    public void setTypeOfMaterial(String typeOfMaterial) {
        this.typeOfMaterial = typeOfMaterial;
    }
}

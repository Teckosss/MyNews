package com.deguffroy.adrien.mynews.Models.News;

/**
 * Created by Adrien Deguffroy on 31/05/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Media{
    @SerializedName("media-metadata")
    @Expose
    private final List<MediaMetadata> mediaMetadata = null;

    public List<MediaMetadata> getMediaMetadata() {
        return mediaMetadata;
    }
}

package com.deguffroy.adrien.mynews.Models.MostPopular;

/**
 * Created by Adrien Deguffroy on 26/05/2018.
 */

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Media {
    @SerializedName("media-metadata")
    @Expose
    private List<MediaMetadata> mediaMetadata = null;

    public List<MediaMetadata> getMediaMetadata() {
        return mediaMetadata;
    }

    public void setMediaMetadata(List<MediaMetadata> mediaMetadata) {
        this.mediaMetadata = mediaMetadata;
    }
}

package io.github.westonal.giphyapi.dto;

import com.google.gson.annotations.SerializedName;

public class Gif {

    @SerializedName("id")
    private String id;

    @SerializedName("images")
    private Images images;

    public String getId() {
        return id;
    }

    public Images getImages() {
        return images;
    }
}

package io.github.westonal.giphyapi.dto;

import com.google.gson.annotations.SerializedName;

public class GifImage {

    @SerializedName("url")
    private String url;

    public String getUrl() {
        return url;
    }
}
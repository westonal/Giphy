package io.github.westonal.giphyapi.dto;

import com.google.gson.annotations.SerializedName;

public class Images {
    @SerializedName("fixed_width")
    private GifImage fixedWidth;

    public GifImage getFixedWidth() {
        return fixedWidth;
    }
}

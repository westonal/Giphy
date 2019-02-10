package io.github.westonal.giphyapi.dto;

import com.google.gson.annotations.SerializedName;

public final class Images {

    @SerializedName("fixed_width")
    private GifImage fixedWidth;

    @SerializedName("original")
    private GifImage original;

    public GifImage getFixedWidth() {
        return fixedWidth;
    }

    public GifImage getOriginal() {
        return original;
    }
}

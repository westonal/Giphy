package io.github.westonal.giphyapi.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrendingResponse {

    @SerializedName("data")
    private List<Gif> gifs;

    @SerializedName("pagination")
    private Pagination pagination;

    public Pagination getPagination() {
        return pagination;
    }

    public List<Gif> getGifs() {
        return gifs;
    }
}

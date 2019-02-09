package io.github.westonal.giphyapi.dto;

import com.google.gson.annotations.SerializedName;

public class TrendingResponse {

    @SerializedName("pagination")
    private Pagination pagination;

    public Pagination getPagination() {
        return pagination;
    }
}

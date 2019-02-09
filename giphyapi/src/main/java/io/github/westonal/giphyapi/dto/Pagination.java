package io.github.westonal.giphyapi.dto;

import com.google.gson.annotations.SerializedName;

public class Pagination {

    @SerializedName("total_count")
    private int totalCount;

    @SerializedName("count")
    private int count;

    @SerializedName("offset")
    private int offset;

    public int getTotalCount() {
        return totalCount;
    }

    public int getCount() {
        return count;
    }

    public int getOffset() {
        return offset;
    }
}

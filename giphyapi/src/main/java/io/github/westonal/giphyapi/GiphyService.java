package io.github.westonal.giphyapi;

import io.github.westonal.giphyapi.dto.PaginationResponse;
import retrofit2.Call;

public final class GiphyService {

    private final GiphyApi giphyApi;
    private final String apiKey;

    public GiphyService(final GiphyApi giphyApi, final String apiKey) {
        this.giphyApi = giphyApi;
        this.apiKey = apiKey;
    }

    public Call<PaginationResponse> getTrending(final int limit, final int offset) {
        return giphyApi.trending(apiKey, limit, offset);
    }

    public Call<PaginationResponse> getSearch(final String query, final int limit, final int offset) {
        return giphyApi.search(apiKey, query, limit, offset);
    }
}

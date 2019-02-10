package io.github.westonal.giphyapi;

import io.github.westonal.giphyapi.dto.TrendingResponse;
import retrofit2.Call;

public final class GiphyService {

    private final GiphyApi giphyApi;
    private final String apiKey;

    public GiphyService(final GiphyApi giphyApi, final String apiKey) {
        this.giphyApi = giphyApi;
        this.apiKey = apiKey;
    }

    public Call<TrendingResponse> getTrending(final int limit, final int offset) {
        return giphyApi.trending(apiKey, limit, offset);
    }
}

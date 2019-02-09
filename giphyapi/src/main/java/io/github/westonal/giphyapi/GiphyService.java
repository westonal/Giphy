package io.github.westonal.giphyapi;

import io.github.westonal.giphyapi.dto.TrendingResponse;
import io.reactivex.Single;

public class GiphyService {

    private final GiphyApi giphyApi;
    private final String apiKey;

    public GiphyService(final GiphyApi giphyApi, final String apiKey) {
        this.giphyApi = giphyApi;
        this.apiKey = apiKey;
    }

    public Single<TrendingResponse> getTrending(final int offset) {
        return giphyApi.trending(apiKey, 25, offset);
    }
}

package io.github.westonal.giphyapi;

import io.github.westonal.giphyapi.dto.TrendingResponse;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GiphyApi {

    @GET("trending")
    Single<TrendingResponse> trending(
            @Query("api_key") String apiKey,
            @Query("limit") int limit,
            @Query("offset") int offset
    );

}

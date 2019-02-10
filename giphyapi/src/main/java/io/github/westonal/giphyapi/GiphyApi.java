package io.github.westonal.giphyapi;

import io.github.westonal.giphyapi.dto.TrendingResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GiphyApi {

    @GET("trending")
    Call<TrendingResponse> trending(
            @Query("api_key") String apiKey,
            @Query("limit") int limit,
            @Query("offset") int offset
    );

}

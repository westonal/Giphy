package io.github.westonal.giphyapi;

import io.github.westonal.giphyapi.dto.PaginationResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GiphyApi {

    @GET("trending")
    Call<PaginationResponse> trending(
            @Query("api_key") String apiKey,
            @Query("limit") int limit,
            @Query("offset") int offset
    );

    @GET("search")
    Call<PaginationResponse> search(
            @Query("api_key") String apiKey,
            @Query("q") String query,
            @Query("limit") int limit,
            @Query("offset") int offset
    );

}

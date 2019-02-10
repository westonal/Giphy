package io.github.westonal.alansgiphysearch.gifdata;

import androidx.annotation.NonNull;
import io.github.westonal.alansgiphysearch.PaginatedGiphyDataSource;
import io.github.westonal.giphyapi.GiphyService;
import io.github.westonal.giphyapi.dto.PaginationResponse;
import retrofit2.Call;

final class TrendingDataSource extends PaginatedGiphyDataSource {

    private final GiphyService giphyService;

    TrendingDataSource(final GiphyService giphyService) {
        super();
        this.giphyService = giphyService;
    }

    @Override
    protected Call<PaginationResponse> loadInitial(@NonNull LoadInitialParams<Integer> params) {
        return giphyService.getTrending(params.requestedLoadSize, 0);
    }

    @Override
    protected Call<PaginationResponse> loadAfter(@NonNull LoadParams<Integer> params) {
        return giphyService.getTrending(params.requestedLoadSize, params.key);
    }
}

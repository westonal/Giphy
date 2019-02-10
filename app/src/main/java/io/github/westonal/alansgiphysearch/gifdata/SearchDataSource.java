package io.github.westonal.alansgiphysearch.gifdata;

import androidx.annotation.NonNull;
import io.github.westonal.alansgiphysearch.PaginatedGiphyDataSource;
import io.github.westonal.giphyapi.GiphyService;
import io.github.westonal.giphyapi.dto.PaginationResponse;
import retrofit2.Call;

final class SearchDataSource extends PaginatedGiphyDataSource {

    private final GiphyService giphyService;
    private final String searchTerm;

    SearchDataSource(final GiphyService giphyService, final String searchTerm) {
        super();
        this.giphyService = giphyService;
        this.searchTerm = searchTerm;
    }

    @Override
    protected Call<PaginationResponse> loadInitial(@NonNull LoadInitialParams<Integer> params) {
        return giphyService.getSearch(searchTerm, params.requestedLoadSize, 0);
    }

    @Override
    protected Call<PaginationResponse> loadAfter(@NonNull LoadParams<Integer> params) {
        return giphyService.getSearch(searchTerm, params.requestedLoadSize, params.key);
    }
}

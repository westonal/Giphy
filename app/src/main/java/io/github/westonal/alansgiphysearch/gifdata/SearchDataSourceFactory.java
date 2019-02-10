package io.github.westonal.alansgiphysearch.gifdata;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;
import io.github.westonal.giphyapi.GiphyService;
import io.github.westonal.giphyapi.dto.Gif;

final class SearchDataSourceFactory extends GifDataSourceFactory {

    private final String searchTerm;
    private final GiphyService giphyService;

    SearchDataSourceFactory(final GiphyService giphyService, final String searchTerm) {
        this.searchTerm = searchTerm;
        this.giphyService = giphyService;
    }

    @NonNull
    @Override
    public DataSource<Integer, Gif> create() {
        final SearchDataSource dataSource = new SearchDataSource(giphyService, searchTerm);
        lastCreated.postValue(dataSource);
        return dataSource;
    }
}

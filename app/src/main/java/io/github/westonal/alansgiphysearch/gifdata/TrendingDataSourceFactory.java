package io.github.westonal.alansgiphysearch.gifdata;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;
import io.github.westonal.giphyapi.GiphyService;
import io.github.westonal.giphyapi.dto.Gif;

final class TrendingDataSourceFactory extends GifDataSourceFactory {

    private final GiphyService giphyService;

    @Inject
    TrendingDataSourceFactory(final GiphyService giphyService) {
        this.giphyService = giphyService;
    }

    @NonNull
    @Override
    public DataSource<Integer, Gif> create() {
        final TrendingDataSource dataSource = new TrendingDataSource(giphyService);
        lastCreated.postValue(dataSource);
        return dataSource;
    }
}

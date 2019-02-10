package io.github.westonal.alansgiphysearch.trending;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;
import io.github.westonal.giphyapi.GiphyService;
import io.github.westonal.giphyapi.dto.Gif;
import io.reactivex.disposables.CompositeDisposable;

final class GifDataSourceFactory extends DataSource.Factory<Integer, Gif> {

    private final GiphyService giphyService;

    @Inject
    GifDataSourceFactory(final GiphyService giphyService) {
        this.giphyService = giphyService;
    }

    @NonNull
    @Override
    public DataSource<Integer, Gif> create() {
        return new TrendingDataSource(giphyService, new CompositeDisposable());
    }
}

package io.github.westonal.alansgiphysearch.trending;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import io.github.westonal.giphyapi.GiphyService;
import io.github.westonal.giphyapi.dto.Gif;

final class GifDataSourceFactory extends DataSource.Factory<Integer, Gif> {

    private final GiphyService giphyService;

    private final MutableLiveData<TrendingDataSource> lastCreated = new MutableLiveData<>();

    @Inject
    GifDataSourceFactory(final GiphyService giphyService) {
        this.giphyService = giphyService;
    }

    @NonNull
    @Override
    public DataSource<Integer, Gif> create() {
        final TrendingDataSource dataSource = new TrendingDataSource(giphyService);
        lastCreated.postValue(dataSource);
        return dataSource;
    }

    LiveData<TrendingDataSource> getLastCreatedDataSource() {
        return lastCreated;
    }
}

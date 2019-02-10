package io.github.westonal.alansgiphysearch.trending;

import android.os.Handler;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import io.github.westonal.giphyapi.GiphyService;
import io.github.westonal.giphyapi.dto.Gif;

public final class TrendingViewModel extends ViewModel {

    private final GifDataSourceFactory factory;
    private final LiveData<PagedList<Gif>> gifs;

    @Inject
    TrendingViewModel(final GiphyService giphyService) {
        factory = new GifDataSourceFactory(giphyService);

        PagedList.Config config = new PagedList.Config.Builder()
                .setPageSize(25)
                .setInitialLoadSizeHint(25)
                .setEnablePlaceholders(false)
                .build();

        gifs = new LivePagedListBuilder<>(factory, config).build();
    }

    public LiveData<PagedList<Gif>> getGifs() {
        return gifs;
    }

    public LiveData<NetworkState> getNetworkState() {
        return Transformations.switchMap(factory.getLastCreatedDataSource(), TrendingDataSource::getNetworkState);
    }

    public void retry(final Handler onHandler) {
        final TrendingDataSource value = factory.getLastCreatedDataSource().getValue();
        if (value != null) {
            final Runnable retry = value.getRetry();
            if (retry != null) {
                onHandler.post(retry);
            }
        }
    }
}

package io.github.westonal.alansgiphysearch.gifdata;

import android.os.Handler;

import java.util.Objects;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import io.github.westonal.alansgiphysearch.PaginatedGiphyDataSource;
import io.github.westonal.giphyapi.GiphyService;
import io.github.westonal.giphyapi.dto.Gif;

public final class GifListViewModel extends ViewModel {

    private final PagedList.Config config;
    private final MutableLiveData<GifDataSourceFactory> factory = new MutableLiveData<>();
    private final GiphyService giphyService;
    private final MutableLiveData<LiveData<PagedList<Gif>>> gifs = new MutableLiveData<>();
    private String searchTerm;

    @Inject
    GifListViewModel(final GiphyService giphyService) {
        this.giphyService = giphyService;

        config = new PagedList.Config.Builder()
                .setPageSize(25)
                .setInitialLoadSizeHint(25)
                .setEnablePlaceholders(false)
                .build();

        updateDataSource(null);
    }

    public LiveData<PagedList<Gif>> getGifs() {
        return Transformations.switchMap(gifs, (x) -> x);
    }

    public LiveData<NetworkState> getNetworkState() {
        return Transformations.switchMap(
                Transformations.switchMap(factory, GifDataSourceFactory::getLastCreatedDataSource),
                PaginatedGiphyDataSource::getNetworkState
        );
    }

    public void retry(final Handler onHandler) {
        final PaginatedGiphyDataSource value = factory.getValue().getLastCreatedDataSource().getValue();
        if (value != null) {
            final Runnable retry = value.getRetry();
            if (retry != null) {
                onHandler.post(retry);
            }
        }
    }

    public void setSearchTerm(final String searchTerm) {
        if (!Objects.equals(this.searchTerm, searchTerm)) {
            this.searchTerm = searchTerm;
            updateDataSource(searchTerm);
        }
    }

    private void updateDataSource(final @Nullable String searchTerm) {
        final GifDataSourceFactory newFactory = createDataSourceFactory(searchTerm);
        factory.postValue(newFactory);
        final LiveData<PagedList<Gif>> build = new LivePagedListBuilder<>(newFactory, config).build();
        gifs.postValue(build);
    }

    @NonNull
    private GifDataSourceFactory createDataSourceFactory(final @Nullable String searchTerm) {
        if (searchTerm == null || searchTerm.trim().equals("")) {
            return new TrendingDataSourceFactory(giphyService);
        } else {
            return new SearchDataSourceFactory(giphyService, searchTerm);
        }
    }
}

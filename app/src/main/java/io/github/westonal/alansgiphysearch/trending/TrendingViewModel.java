package io.github.westonal.alansgiphysearch.trending;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import io.github.westonal.giphyapi.GiphyService;
import io.github.westonal.giphyapi.dto.Gif;

public final class TrendingViewModel extends ViewModel {

    private GiphyService giphyService;

    @Inject
    TrendingViewModel(final GiphyService giphyService) {
        this.giphyService = giphyService;
    }

    private LiveData<PagedList<Gif>> gifs;

    public LiveData<PagedList<Gif>> getGifs() {
        if (gifs == null) {
            final DataSource.Factory<Integer, Gif> factory = new GifDataSourceFactory(giphyService);

            PagedList.Config config = new PagedList.Config.Builder()
                    .setPageSize(25)
                    .setInitialLoadSizeHint(25)
                    .setEnablePlaceholders(false)
                    .build();

            gifs = new LivePagedListBuilder<>(factory, config).build();
        }
        return gifs;
    }
}

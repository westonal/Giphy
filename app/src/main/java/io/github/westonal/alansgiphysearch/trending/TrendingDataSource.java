package io.github.westonal.alansgiphysearch.trending;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;
import io.github.westonal.giphyapi.GiphyService;
import io.github.westonal.giphyapi.dto.Gif;
import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

final class TrendingDataSource extends PageKeyedDataSource<Integer, Gif> {

    private final GiphyService giphyService;
    private final CompositeDisposable compositeDisposable;

    TrendingDataSource(GiphyService giphyService,
                       CompositeDisposable compositeDisposable) {
        this.giphyService = giphyService;
        this.compositeDisposable = compositeDisposable;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Gif> callback) {
        Timber.d("getTrending loadInitial %d/%d", params.requestedLoadSize, 0);
        compositeDisposable.add(
                giphyService.getTrending(params.requestedLoadSize, 0)
                        .subscribe(response -> callback.onResult(
                                response.getGifs(),
                                response.getPagination().getOffset(),
                                response.getPagination().getTotalCount(),
                                null,
                                response.getPagination().getOffset() + response.getPagination().getCount()
                                ),
                                Timber::e)
        );
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Gif> callback) {
        Timber.d("getTrending loadBefore %d/%d", params.requestedLoadSize, params.key);
        compositeDisposable.add(
                giphyService.getTrending(params.requestedLoadSize, params.key)
                        .subscribe(response -> callback.onResult(
                                response.getGifs(),
                                response.getPagination().getOffset() + response.getPagination().getCount()
                                ),
                                Timber::e)
        );
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Gif> callback) {
        Timber.d("getTrending loadAfter %d/%d", params.requestedLoadSize, params.key);
        compositeDisposable.add(
                giphyService.getTrending(params.requestedLoadSize, params.key)
                        .subscribe(response -> callback.onResult(
                                response.getGifs(),
                                response.getPagination().getOffset() + response.getPagination().getCount()
                                ),
                                Timber::e)
        );
    }
}

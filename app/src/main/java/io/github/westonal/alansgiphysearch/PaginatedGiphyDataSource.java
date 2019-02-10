package io.github.westonal.alansgiphysearch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;
import io.github.westonal.alansgiphysearch.gifdata.NetworkState;
import io.github.westonal.giphyapi.dto.Gif;
import io.github.westonal.giphyapi.dto.PaginationResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public abstract class PaginatedGiphyDataSource extends PageKeyedDataSource<Integer, Gif> {

    private final MutableLiveData<NetworkState> networkState = new MutableLiveData<>();

    private Runnable retry;

    protected abstract Call<PaginationResponse> loadInitial(@NonNull LoadInitialParams<Integer> params);

    protected abstract Call<PaginationResponse> loadAfter(@NonNull LoadParams<Integer> params);

    public MutableLiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    @Nullable
    public synchronized Runnable getRetry() {
        return retry;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Gif> callback) {
        Timber.d("getTrending loadInitial %d/%d", params.requestedLoadSize, 0);
        loading();
        loadInitial(params)
                .enqueue(new Callback<PaginationResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<PaginationResponse> call, @NonNull Response<PaginationResponse> response) {
                        final PaginationResponse trending = response.body();
                        if (!response.isSuccessful() || trending == null) {
                            Timber.e(response.message());
                            failed(() -> loadInitial(params, callback));
                            return;
                        }
                        callback.onResult(
                                trending.getGifs(),
                                trending.getPagination().getOffset(),
                                trending.getPagination().getTotalCount(),
                                null,
                                trending.getPagination().getOffset() + trending.getPagination().getCount()
                        );
                        loaded();
                    }

                    @Override
                    public void onFailure
                            (@NonNull Call<PaginationResponse> call, @NonNull Throwable t) {
                        Timber.e(t);
                        failed(() -> loadInitial(params, callback));
                    }
                });
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Gif> callback) {
        Timber.d("getTrending loadAfter %d/%d", params.requestedLoadSize, params.key);
        loading();
        loadAfter(params)
                .enqueue(new Callback<PaginationResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<PaginationResponse> call, @NonNull Response<PaginationResponse> response) {
                        final PaginationResponse trending = response.body();
                        if (!response.isSuccessful() || trending == null) {
                            Timber.e(response.message());
                            failed(() -> loadAfter(params, callback));
                            return;
                        }
                        callback.onResult(
                                trending.getGifs(),
                                trending.getPagination().getOffset() + trending.getPagination().getCount()
                        );
                        loaded();
                    }

                    @Override
                    public void onFailure(@NonNull Call<PaginationResponse> call, @NonNull Throwable t) {
                        Timber.e(t);
                        failed(() -> loadAfter(params, callback));
                    }
                });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Gif> callback) {
        throw new RuntimeException("Do not expect load before, we only append");
    }

    private void loading() {
        networkState.postValue(NetworkState.LOADING);
        setRetry(null);
    }

    private void loaded() {
        networkState.postValue(NetworkState.LOADED);
        setRetry(null);
    }

    private void failed(Runnable runnable) {
        networkState.postValue(NetworkState.FAILED);
        setRetry(runnable);
    }

    private synchronized void setRetry(Runnable runnable) {
        retry = runnable;
    }
}

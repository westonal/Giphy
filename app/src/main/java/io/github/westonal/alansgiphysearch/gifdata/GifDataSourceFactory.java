package io.github.westonal.alansgiphysearch.gifdata;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import io.github.westonal.alansgiphysearch.PaginatedGiphyDataSource;
import io.github.westonal.giphyapi.dto.Gif;

abstract class GifDataSourceFactory extends DataSource.Factory<Integer, Gif> {

    final MutableLiveData<PaginatedGiphyDataSource> lastCreated = new MutableLiveData<>();

    LiveData<PaginatedGiphyDataSource> getLastCreatedDataSource() {
        return lastCreated;
    }
}

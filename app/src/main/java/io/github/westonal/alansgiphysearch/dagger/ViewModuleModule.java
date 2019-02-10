package io.github.westonal.alansgiphysearch.dagger;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import io.github.westonal.alansgiphysearch.trending.TrendingViewModel;

/**
 * Adapted from https://proandroiddev.com/viewmodel-with-dagger2-architecture-components-2e06f06c9455
 */
@Module
abstract class ViewModuleModule {

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);

    @Binds
    @IntoMap
    @ViewModelKey(TrendingViewModel.class)
    abstract ViewModel postTrendingViewModel(TrendingViewModel trendingViewModel);
}

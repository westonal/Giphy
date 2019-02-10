package io.github.westonal.alansgiphysearch.dagger;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import io.github.westonal.alansgiphysearch.gifdata.GifListViewModel;

/**
 * Adapted from https://proandroiddev.com/viewmodel-with-dagger2-architecture-components-2e06f06c9455
 */
@Module
abstract class ViewModuleModule {

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(final ViewModelFactory factory);

    @Binds
    @IntoMap
    @ViewModelKey(GifListViewModel.class)
    abstract ViewModel postGifListViewModel(final GifListViewModel gifListViewModel);
}

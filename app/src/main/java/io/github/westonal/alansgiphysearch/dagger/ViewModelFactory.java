package io.github.westonal.alansgiphysearch.dagger;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * Adapted from https://proandroiddev.com/viewmodel-with-dagger2-architecture-components-2e06f06c9455
 */
@Singleton
class ViewModelFactory implements ViewModelProvider.Factory {

    private final Map<Class<? extends ViewModel>, Provider<ViewModel>> viewModelProviders;

    @Inject
    ViewModelFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> viewModelProviders) {
        this.viewModelProviders = viewModelProviders;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        //noinspection unchecked,ConstantConditions
        return (T) viewModelProviders.get(modelClass).get();
    }
}

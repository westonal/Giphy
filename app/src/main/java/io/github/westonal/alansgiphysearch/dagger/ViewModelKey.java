package io.github.westonal.alansgiphysearch.dagger;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.lifecycle.ViewModel;
import dagger.MapKey;

/**
 * Adapted from https://proandroiddev.com/viewmodel-with-dagger2-architecture-components-2e06f06c9455
 */
@MapKey
@Retention(RetentionPolicy.RUNTIME)
@interface ViewModelKey {

    Class<? extends ViewModel> value();

}

package io.github.westonal.alansgiphysearch.network;

import androidx.annotation.NonNull;
import okhttp3.Interceptor;
import okhttp3.Response;

abstract class NotExpectedInterceptor implements Interceptor {

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) {
        throw new RuntimeException("Not expected to be called in release mode");
    }
}

package io.github.westonal.alansgiphysearch.network;

import java.io.IOException;

import androidx.annotation.NonNull;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.internal.http.RealResponseBody;
import okio.GzipSource;
import okio.Okio;
import timber.log.Timber;

/**
 * Based on https://stackoverflow.com/a/51902833/360211
 */
public final class UnzippingInterceptor implements Interceptor {

    @NonNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        return unzip(response);
    }

    private static Response unzip(final Response response) {
        if (response.body() == null) {
            return response;
        }

        if ("gzip" .equals(response.headers().get("Content-Encoding"))) {
            final long contentLength = response.body().contentLength();
            final GzipSource responseBody = new GzipSource(response.body().source());
            final Headers strippedHeaders = response.headers().newBuilder()
                    .removeAll("Content-Encoding")
                    .build();
            Timber.d("Unzipping");
            final MediaType mediaType = response.body().contentType();
            if (mediaType == null) {
                throw new RuntimeException("Expected a media type");
            }
            return response
                    .newBuilder()
                    .headers(strippedHeaders)
                    .body(new RealResponseBody(
                            mediaType.toString(),
                            contentLength,
                            Okio.buffer(responseBody)
                    ))
                    .build();
        } else {
            return response;
        }
    }
}

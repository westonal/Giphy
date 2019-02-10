package io.github.westonal.alansgiphysearch.network;

import java.io.IOException;
import java.util.Locale;

import androidx.annotation.NonNull;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import timber.log.Timber;

/**
 * Based on https://github.com/blockchain/My-Wallet-V3-Android: ApiInterceptor.java
 */
public final class ApiInterceptor implements Interceptor {

    @NonNull
    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        final Request request = chain.request();
        final long startTime = System.nanoTime();

        Timber.v("-------------------------------------------------------------------------------");
        Timber.v("Request:\n%s", getRequestLog(request));

        final Response response = chain.proceed(request);
        final long endTime = System.nanoTime();

        final String responseLog = String.format(
                Locale.ENGLISH,
                "Received response from %s in %.1fms%n%s",
                response.request().url(),
                (endTime - startTime) / 1e6d,
                response.headers());


        final String responseMessage = "Response: " + response.code() + "\n" + responseLog + "\n";
        if (response.code() == 200) {
            Timber.v(responseMessage);
        } else {
            Timber.w(responseMessage);
        }
        Timber.v("-------------------------------------------------------------------------------");

        return response;
    }

    @NonNull
    private String getRequestLog(Request request) {
        String requestLog = String.format(
                "Sending request of type %s to %s with headers %s",
                request.method(),
                request.url(),
                request.headers());

        if (request.method().compareToIgnoreCase("post") == 0) {
            requestLog = "\n" + requestLog + "\n" + requestBodyToString(request.body());
        }
        return requestLog;
    }

    private String requestBodyToString(final RequestBody request) {
        try (Buffer buffer = new Buffer()) {
            if (request != null) {
                request.writeTo(buffer);
                return buffer.readUtf8();
            } else {
                return "";
            }
        } catch (final IOException e) {
            return "IOException reading request body";
        }
    }
}

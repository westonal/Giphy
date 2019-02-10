package io.github.westonal.alansgiphysearch.dagger;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.github.westonal.alansgiphysearch.BuildConfig;
import io.github.westonal.alansgiphysearch.network.ApiInterceptor;
import io.github.westonal.alansgiphysearch.network.UnzippingInterceptor;
import io.github.westonal.giphyapi.GiphyApi;
import io.github.westonal.giphyapi.GiphyService;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
final class ServiceModule {

    @Provides
    @Singleton
    static OkHttpClient provideOkHttpClient() {
        final OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            okHttpClient.addNetworkInterceptor(new ApiInterceptor());
            okHttpClient.addNetworkInterceptor(new UnzippingInterceptor());
        }
        return okHttpClient.build();
    }

    @Provides
    @Singleton
    @Named("giphy")
    static Retrofit provideGiphyRetrofit(final OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.GIPHY_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    static GiphyApi provideGiphyApi(@Named("giphy") final Retrofit retrofit) {
        return retrofit.create(GiphyApi.class);
    }

    @Provides
    @Singleton
    static GiphyService provideGiphyService(final GiphyApi giphyApi) {
        return new GiphyService(giphyApi, BuildConfig.GIPHY_API_KEY);
    }
}

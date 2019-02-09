package io.github.westonal.alansgiphysearch.dagger;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.github.westonal.alansgiphysearch.BuildConfig;
import io.github.westonal.giphyapi.GiphyApi;
import io.github.westonal.giphyapi.GiphyService;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public final class ServiceModule {

    @Provides
    @Singleton
    static OkHttpClient provideOkHttpClient() {
        return new OkHttpClient();
    }

    @Provides
    @Singleton
    @Named("giphy")
    static Retrofit provideGiphyRetrofit(final OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.GIPHY_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
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

package io.github.westonal.alansgiphysearch;

import android.app.Application;

import io.github.westonal.alansgiphysearch.dagger.AppComponent;
import io.github.westonal.alansgiphysearch.dagger.DaggerAppComponent;
import timber.log.Timber;

public final class App extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        appComponent = createAppComponent();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    private static AppComponent createAppComponent() {
        return DaggerAppComponent
                .builder()
                .build();
    }
}

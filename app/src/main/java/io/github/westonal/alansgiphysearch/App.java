package io.github.westonal.alansgiphysearch;

import android.app.Application;

import io.github.westonal.alansgiphysearch.dagger.AppComponent;
import io.github.westonal.alansgiphysearch.dagger.DaggerAppComponent;
import io.github.westonal.alansgiphysearch.dagger.ServiceModule;

public final class App extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = createMyComponent();
    }

    private AppComponent createMyComponent() {
        return DaggerAppComponent
                .builder()
                .serviceModule(new ServiceModule())
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}

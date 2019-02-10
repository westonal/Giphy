package io.github.westonal.alansgiphysearch.dagger;

import javax.inject.Singleton;

import dagger.Component;
import io.github.westonal.alansgiphysearch.TrendingFragment;

@Singleton
@Component(modules = {
        ServiceModule.class,
        ViewModuleModule.class
})
public interface AppComponent {

    void inject(TrendingFragment trendingFragment);
}

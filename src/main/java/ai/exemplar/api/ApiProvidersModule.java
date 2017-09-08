package ai.exemplar.api;

import ai.exemplar.api.spotify.SpotifyApiProvider;
import ai.exemplar.api.spotify.impl.SpotifyApiProviderImpl;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class ApiProvidersModule {

    @Provides
    @Singleton
    public static SpotifyApiProvider provideSpotifyApiProvider() {
        return new SpotifyApiProviderImpl();
    }
}

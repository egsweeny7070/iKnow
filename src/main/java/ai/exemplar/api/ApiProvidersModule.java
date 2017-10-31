package ai.exemplar.api;

import ai.exemplar.api.auth0.Auth0ApiProvider;
import ai.exemplar.api.auth0.impl.Auth0ApiProviderImpl;
import ai.exemplar.api.spotify.SpotifyApiProvider;
import ai.exemplar.api.spotify.impl.SpotifyApiProviderImpl;
import ai.exemplar.api.square.SquareApiProvider;
import ai.exemplar.api.square.impl.SquareApiProviderImpl;
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

    @Provides
    @Singleton
    public static SquareApiProvider provideSquareApiProvider() {
        return new SquareApiProviderImpl();
    }

    @Provides
    @Singleton
    public static Auth0ApiProvider provideAuth0ApiProvider() {
        return new Auth0ApiProviderImpl();
    }
}

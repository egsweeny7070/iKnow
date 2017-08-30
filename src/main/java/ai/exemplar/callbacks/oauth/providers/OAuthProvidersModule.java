package ai.exemplar.callbacks.oauth.providers;

import ai.exemplar.callbacks.oauth.providers.spotify.SpotifyClientCredentials;
import ai.exemplar.callbacks.oauth.providers.spotify.SpotifyOAuthProvider;
import ai.exemplar.callbacks.oauth.providers.square.SquareClientCredentials;
import ai.exemplar.callbacks.oauth.providers.square.SquareOAuthProvider;
import ai.exemplar.callbacks.oauth.providers.values.OAuthClientCredentials;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;

@Module
public class OAuthProvidersModule {

    @Provides
    @SquareClientCredentials
    public static OAuthClientCredentials provideSquareClientCredentials() {
        return new OAuthClientCredentials(
                "sq0idp-JOcI7wPMrutdlGthWLCUYQ",
                "sq0csp-doEMvfali8Lc52eX9cu2Ygb0Zzx5lSGbrdX5HkNupPY"
        );
    }

    @Provides
    @IntoMap
    @StringKey(SquareOAuthProvider.PROVIDER_NAME)
    static OAuthProvider provideSquareOAuthProvider(
            SquareOAuthProvider provider
    ) {
        return provider;
    }

    @Provides
    @SpotifyClientCredentials
    public static OAuthClientCredentials provideSpotifyClientCredentials() {
        return new OAuthClientCredentials(
                "84bd052b5f844708861f1c3dc8685633",
                "18e4b02a14b64bfa8e10027f93911742"
        );
    }

    @Provides
    @IntoMap
    @StringKey(SpotifyOAuthProvider.PROVIDER_NAME)
    static OAuthProvider provideSpotifyOAuthProvider(
            SpotifyOAuthProvider provider
    ) {
        return provider;
    }
}

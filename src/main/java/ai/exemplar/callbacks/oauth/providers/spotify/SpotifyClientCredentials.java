package ai.exemplar.callbacks.oauth.providers.spotify;

import javax.inject.Qualifier;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface SpotifyClientCredentials {
}

package ai.exemplar.authorization;

import ai.exemplar.authorization.auth0.Auth0AuthorizationService;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class AuthorizationModule {

    @Provides
    @Singleton
    public static AuthorizationService provideAuthorizationService() {
        return new Auth0AuthorizationService();
    }
}

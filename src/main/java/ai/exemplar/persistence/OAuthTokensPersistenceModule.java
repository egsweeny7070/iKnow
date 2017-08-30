package ai.exemplar.persistence;

import dagger.Module;
import dagger.Provides;
import ai.exemplar.persistence.dynamodb.DynamoDBOAuthTokenRepository;

import javax.inject.Singleton;

@Module
public class OAuthTokensPersistenceModule {

    @Provides
    @Singleton
    public static OAuthTokenRepository provideOAuthTokensRepository(
            DynamoDBOAuthTokenRepository oAuthTokensRepository
    ) {
        return oAuthTokensRepository;
    }
}

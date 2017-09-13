package ai.exemplar.persistence;

import ai.exemplar.persistence.dynamodb.DynamoDBSpotifyHistoryRepository;
import ai.exemplar.persistence.dynamodb.DynamoDBSquareLocationsRepository;
import ai.exemplar.persistence.dynamodb.DynamoDBSquarePaymentsRepository;
import dagger.Module;
import dagger.Provides;
import ai.exemplar.persistence.dynamodb.DynamoDBOAuthTokenRepository;

import javax.inject.Singleton;

@Module
public class DynamoDBPersistenceModule {

    @Provides
    @Singleton
    public static OAuthTokenRepository provideOAuthTokensRepository(
            DynamoDBOAuthTokenRepository oAuthTokensRepository
    ) {
        return oAuthTokensRepository;
    }

    @Provides
    @Singleton
    public static SpotifyHistoryRepository provideSpotifyHistoryRepository(
            DynamoDBSpotifyHistoryRepository spotifyHistoryRepository
    ) {
        return spotifyHistoryRepository;
    }

    @Provides
    @Singleton
    public static SquareLocationRepository provideSquareLocationRepository(
            DynamoDBSquareLocationsRepository squareLocationsRepository
    ) {
        return squareLocationsRepository;
    }

    @Provides
    @Singleton
    public static SquarePaymentsRepository provideSquarePaymentsRepository(
            DynamoDBSquarePaymentsRepository squarePaymentsRepository
    ) {
        return squarePaymentsRepository;
    }
}

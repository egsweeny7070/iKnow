package ai.exemplar.persistence;

import ai.exemplar.persistence.dynamodb.*;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class DynamoDBPersistenceModule {

    @Provides
    @Singleton
    public static AccountsRepository provideAccountsRepository(
            DynamoDBAccountsRepository accountsRepository
    ) {
        return accountsRepository;
    }

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

    @Provides
    @Singleton
    public static PaymentsAnalyticsRepository providePaymentsAnalyticsRepository(
            DynamoDBPaymentsAnalyticsRepository paymentsAnalyticsRepository
    ) {
        return paymentsAnalyticsRepository;
    }

    @Provides
    @Singleton
    public static TracksAnalyticsRepository provideTracksAnalyticsRepository(
            DynamoDBTracksAnalyticsRepository tracksAnalyticsRepository
    ) {
        return tracksAnalyticsRepository;
    }
}

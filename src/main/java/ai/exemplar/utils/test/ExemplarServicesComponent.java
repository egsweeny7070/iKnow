package ai.exemplar.utils.test;

import ai.exemplar.api.ApiProvidersModule;
import ai.exemplar.api.spotify.SpotifyApiProvider;
import ai.exemplar.api.square.SquareApiProvider;
import ai.exemplar.dependencies.DynamoDBModule;
import ai.exemplar.persistence.*;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {
        DynamoDBModule.class,
        DynamoDBPersistenceModule.class,
        ApiProvidersModule.class
})
public interface ExemplarServicesComponent {

    SpotifyApiProvider spotifyApiProvider();

    SpotifyHistoryRepository spotifyHistoryRepository();

    SquareApiProvider squareApiProvider();

    SquareLocationRepository squareLocationRepository();

    SquarePaymentsRepository squarePaymentsRepository();

    OAuthTokenRepository oAuthTokenRepository();
}

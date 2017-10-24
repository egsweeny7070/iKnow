package ai.exemplar.utils.test;

import ai.exemplar.api.ApiProvidersModule;
import ai.exemplar.api.spotify.SpotifyApiProvider;
import ai.exemplar.api.square.SquareApiProvider;
import ai.exemplar.dependencies.AmazonKinesisModule;
import ai.exemplar.dependencies.DynamoDBModule;
import ai.exemplar.persistence.*;
import ai.exemplar.streams.StreamsAppender;
import ai.exemplar.streams.StreamsModule;
import ai.exemplar.streams.kinesis.KinesisStreamsModule;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {
        DynamoDBModule.class,
        AmazonKinesisModule.class,
        DynamoDBPersistenceModule.class,
        ApiProvidersModule.class,
        KinesisStreamsModule.class,
        StreamsModule.class
})
public interface ExemplarServicesComponent {

    SpotifyApiProvider spotifyApiProvider();

    SpotifyHistoryRepository spotifyHistoryRepository();

    SquareApiProvider squareApiProvider();

    SquareLocationRepository squareLocationRepository();

    SquarePaymentsRepository squarePaymentsRepository();

    OAuthTokenRepository oAuthTokenRepository();

    StreamsAppender streamsAppender();
}

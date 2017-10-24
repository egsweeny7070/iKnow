package ai.exemplar.data;

import ai.exemplar.api.ApiProvidersModule;
import ai.exemplar.common.CommonServicesModule;
import ai.exemplar.data.fetchers.DataFetchersModule;
import ai.exemplar.data.service.DataServiceModule;
import ai.exemplar.data.service.ScheduledJobsService;
import ai.exemplar.dependencies.AmazonKinesisModule;
import ai.exemplar.dependencies.DynamoDBModule;
import ai.exemplar.persistence.DynamoDBPersistenceModule;
import ai.exemplar.streams.StreamsModule;
import ai.exemplar.streams.kinesis.KinesisStreamsModule;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {
        DynamoDBModule.class,
        DynamoDBPersistenceModule.class,
        ApiProvidersModule.class,
        CommonServicesModule.class,
        AmazonKinesisModule.class,
        KinesisStreamsModule.class,
        StreamsModule.class,
        DataFetchersModule.class,
        DataServiceModule.class
})
public interface ExemplarDataComponent {

    ScheduledJobsService scheduledJobsService();
}

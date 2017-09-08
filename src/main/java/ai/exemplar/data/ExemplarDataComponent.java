package ai.exemplar.data;

import ai.exemplar.api.ApiProvidersModule;
import ai.exemplar.data.fetchers.DataFetchersModule;
import ai.exemplar.data.service.DataServiceModule;
import ai.exemplar.data.service.ScheduledJobsService;
import ai.exemplar.dependencies.DynamoDBModule;
import ai.exemplar.persistence.DynamoDBPersistenceModule;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {
        DynamoDBModule.class,
        DynamoDBPersistenceModule.class,
        ApiProvidersModule.class,
        DataFetchersModule.class,
        DataServiceModule.class
})
public interface ExemplarDataComponent {

    ScheduledJobsService scheduledJobsService();
}

package ai.exemplar.data.service;

import ai.exemplar.data.service.impl.ScheduledJobsServiceImpl;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class DataServiceModule {

    @Provides
    @Singleton
    public static ScheduledJobsService provideScheduledJobsService(
            ScheduledJobsServiceImpl scheduledJobsService
    ) {
        return scheduledJobsService;
    }
}

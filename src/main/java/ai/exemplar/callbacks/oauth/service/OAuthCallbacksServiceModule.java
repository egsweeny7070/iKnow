package ai.exemplar.callbacks.oauth.service;

import ai.exemplar.callbacks.oauth.service.impl.OAuthCallbacksServiceImpl;
import dagger.Module;
import dagger.Provides;
import ai.exemplar.callbacks.oauth.service.impl.ScheduledJobsServiceImpl;

import javax.inject.Singleton;

@Module
public class OAuthCallbacksServiceModule {

    @Provides
    @Singleton
    public static ScheduledJobsService provideScheduledJobsService(
            ScheduledJobsServiceImpl scheduledJobsService
    ) {
        return scheduledJobsService;
    }

    @Provides
    @Singleton
    public static OAuthCallbacksService provideOAuthCallbacksService(
            OAuthCallbacksServiceImpl oAuthCallbacksService
    ) {
        return oAuthCallbacksService;
    }
}

package ai.exemplar.proxy.service.analytics;

import ai.exemplar.proxy.service.analytics.impl.AnalyticsApiServiceImpl;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class AnalyticsApiServiceModule {

    @Provides
    @Singleton
    public static AnalyticsApiService provideAnalyticsApiService(
            AnalyticsApiServiceImpl analyticsApiService
    ) {
        return analyticsApiService;
    }
}

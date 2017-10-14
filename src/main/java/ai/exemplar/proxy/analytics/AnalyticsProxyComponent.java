package ai.exemplar.proxy.analytics;

import ai.exemplar.analytics.AnalyticsModule;
import ai.exemplar.authorization.AuthorizationModule;
import ai.exemplar.dependencies.DynamoDBModule;
import ai.exemplar.persistence.DynamoDBPersistenceModule;
import ai.exemplar.proxy.service.analytics.AnalyticsApiService;
import ai.exemplar.proxy.service.analytics.AnalyticsApiServiceModule;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {
        DynamoDBModule.class,
        DynamoDBPersistenceModule.class,
        AuthorizationModule.class,
        AnalyticsModule.class,
        AnalyticsApiServiceModule.class
})
public interface AnalyticsProxyComponent {

    AnalyticsApiService analyticsApiService();
}

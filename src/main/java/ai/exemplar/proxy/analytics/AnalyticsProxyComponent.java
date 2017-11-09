package ai.exemplar.proxy.analytics;

import ai.exemplar.analytics.AnalyticsModule;
import ai.exemplar.api.ApiProvidersModule;
import ai.exemplar.authorization.AuthorizationModule;
import ai.exemplar.common.CommonServicesModule;
import ai.exemplar.dependencies.AmazonS3Module;
import ai.exemplar.dependencies.DynamoDBModule;
import ai.exemplar.persistence.DynamoDBPersistenceModule;
import ai.exemplar.proxy.service.analytics.AnalyticsApiService;
import ai.exemplar.proxy.service.analytics.AnalyticsApiServiceModule;
import ai.exemplar.storage.CorrelationsStorageModule;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {
        DynamoDBModule.class,
        DynamoDBPersistenceModule.class,
        ApiProvidersModule.class,
        AuthorizationModule.class,
        AmazonS3Module.class,
        CorrelationsStorageModule.class,
        AnalyticsModule.class,
        AnalyticsApiServiceModule.class
})
public interface AnalyticsProxyComponent {

    AnalyticsApiService analyticsApiService();
}

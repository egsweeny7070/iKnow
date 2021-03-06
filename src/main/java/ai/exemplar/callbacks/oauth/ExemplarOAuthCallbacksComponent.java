package ai.exemplar.callbacks.oauth;

import ai.exemplar.api.ApiProvidersModule;
import ai.exemplar.callbacks.oauth.providers.OAuthProvidersModule;
import ai.exemplar.callbacks.oauth.service.OAuthCallbacksService;
import ai.exemplar.common.CommonServicesModule;
import ai.exemplar.persistence.DynamoDBPersistenceModule;
import ai.exemplar.callbacks.oauth.service.ScheduledJobsService;
import ai.exemplar.callbacks.oauth.service.OAuthCallbacksServiceModule;
import ai.exemplar.dependencies.DynamoDBModule;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {
        DynamoDBModule.class,
        DynamoDBPersistenceModule.class,
        ApiProvidersModule.class,
        CommonServicesModule.class,
        OAuthProvidersModule.class,
        OAuthCallbacksServiceModule.class
})
public interface ExemplarOAuthCallbacksComponent {

    OAuthCallbacksService oAuthCallbacksService();

    ScheduledJobsService scheduledJobsService();
}

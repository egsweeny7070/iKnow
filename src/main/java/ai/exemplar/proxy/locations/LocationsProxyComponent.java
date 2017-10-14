package ai.exemplar.proxy.locations;

import ai.exemplar.api.ApiProvidersModule;
import ai.exemplar.authorization.AuthorizationModule;
import ai.exemplar.common.CommonServicesModule;
import ai.exemplar.dependencies.DynamoDBModule;
import ai.exemplar.persistence.DynamoDBPersistenceModule;
import ai.exemplar.proxy.service.locations.LocationsApiServiceModule;
import ai.exemplar.proxy.service.locations.LocationsApiService;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {
        DynamoDBModule.class,
        DynamoDBPersistenceModule.class,
        ApiProvidersModule.class,
        CommonServicesModule.class,
        AuthorizationModule.class,
        LocationsApiServiceModule.class
})
public interface LocationsProxyComponent {

    LocationsApiService locationsApiService();
}

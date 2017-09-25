package ai.exemplar.proxy.service;

import ai.exemplar.proxy.service.impl.LocationsApiServiceImpl;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class ApiServiceModule {

    @Provides
    @Singleton
    public static LocationsApiService provideLocationsApiService(
            LocationsApiServiceImpl locationsApiService
    ) {
        return locationsApiService;
    }
}

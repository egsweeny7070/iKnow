package ai.exemplar.proxy.service.locations;

import ai.exemplar.proxy.service.locations.impl.LocationsApiServiceImpl;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class LocationsApiServiceModule {

    @Provides
    @Singleton
    public static LocationsApiService provideLocationsApiService(
            LocationsApiServiceImpl locationsApiService
    ) {
        return locationsApiService;
    }
}

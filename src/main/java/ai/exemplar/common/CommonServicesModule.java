package ai.exemplar.common;

import ai.exemplar.common.square.SquareLocationsService;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class CommonServicesModule {

    @Provides
    @Singleton
    public static LocationsService provideLocationsService(
            SquareLocationsService locationsService
    ) {
        return locationsService;
    }
}

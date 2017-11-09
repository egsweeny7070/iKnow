package ai.exemplar.storage;

import ai.exemplar.storage.impl.CorrelationsStorageServiceImpl;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class CorrelationsStorageModule {

    @Provides
    @Singleton
    public static CorrelationsStorageService provideCorrelationsStorageService(
            CorrelationsStorageServiceImpl correlationsStorageService
    ) {
        return correlationsStorageService;
    }
}

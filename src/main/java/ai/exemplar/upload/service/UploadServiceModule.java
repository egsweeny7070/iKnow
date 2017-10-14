package ai.exemplar.upload.service;

import ai.exemplar.upload.service.impl.RecordUploadServiceImpl;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class UploadServiceModule {

    @Provides
    @Singleton
    public static RecordUploadService provideRecordUploadService(
            RecordUploadServiceImpl recordUploadService
    ) {
        return recordUploadService;
    }
}

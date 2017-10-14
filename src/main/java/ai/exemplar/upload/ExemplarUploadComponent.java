package ai.exemplar.upload;

import ai.exemplar.dependencies.DynamoDBModule;
import ai.exemplar.persistence.DynamoDBPersistenceModule;
import ai.exemplar.upload.service.RecordUploadService;
import ai.exemplar.upload.service.UploadServiceModule;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {
        DynamoDBModule.class,
        DynamoDBPersistenceModule.class,
        UploadServiceModule.class
})
public interface ExemplarUploadComponent {

    RecordUploadService recordUploadService();
}

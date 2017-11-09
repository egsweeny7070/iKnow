package ai.exemplar.dependencies;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class AmazonS3Module {

    @Provides
    @Singleton
    public static AmazonS3 provideAmazonDynamoDB() {
        return AmazonS3ClientBuilder.standard()
                .withRegion(Regions.US_WEST_2)
                .build();
    }
}

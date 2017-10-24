package ai.exemplar.dependencies;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.AmazonKinesisClientBuilder;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class AmazonKinesisModule {

    @Provides
    @Singleton
    public static AmazonKinesis provideAmazonKinesis() {
        return AmazonKinesisClientBuilder.standard()
                .withRegion(Regions.US_WEST_2)
                .withCredentials(new AWSStaticCredentialsProvider(
                        new BasicAWSCredentials(
                                "AKIAIYRPR3WUGDD3RT4Q",
                                "w1WWM7h6CfKLxx92qUB95DvO0AisgV0uUNapV5CN"
                        )
                ))
                .build();
    }
}

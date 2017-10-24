package ai.exemplar.streams.kinesis;

import dagger.Module;
import dagger.Provides;

@Module
public class KinesisStreamsModule {

    @Provides
    @KinesisStreamName
    public static String provideKinesisStreamName() {
        return "Exemplar_Events";
    }
}

package ai.exemplar.streams;

import ai.exemplar.streams.kinesis.KinesisStreamsAppender;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class StreamsModule {

    @Provides
    @Singleton
    public static StreamsAppender provideStreamsAppender(
            KinesisStreamsAppender streamsAppender
    ) {
        return streamsAppender;
    }
}

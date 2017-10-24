package ai.exemplar.streams;

import ai.exemplar.streams.impl.KinesisStreamsAppender;
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

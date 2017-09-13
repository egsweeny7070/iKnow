package ai.exemplar.data.fetchers;

import ai.exemplar.data.fetchers.spotify.SpotifyDataFetcher;
import ai.exemplar.data.fetchers.square.SquareDataFetcher;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;

@Module
public class DataFetchersModule {

    @Provides
    @IntoMap
    @StringKey(SpotifyDataFetcher.PROVIDER_NAME)
    static DataFetcher provideSpotifyDataFetcher(
            SpotifyDataFetcher fetcher
    ) {
        return fetcher;
    }

    @Provides
    @IntoMap
    @StringKey(SquareDataFetcher.PROVIDER_NAME)
    static DataFetcher provideSquareDataFetcher(
            SquareDataFetcher fetcher
    ) {
        return fetcher;
    }
}

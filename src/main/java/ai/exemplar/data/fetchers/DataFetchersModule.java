package ai.exemplar.data.fetchers;

import ai.exemplar.data.fetchers.spotify.SpotifyDataFetcher;
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
}

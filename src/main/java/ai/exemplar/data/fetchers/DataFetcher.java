package ai.exemplar.data.fetchers;

import ai.exemplar.persistence.model.OAuthToken;

public interface DataFetcher {

    void fetchData(OAuthToken token);
}

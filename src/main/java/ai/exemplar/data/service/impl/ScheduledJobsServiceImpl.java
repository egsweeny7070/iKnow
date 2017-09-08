package ai.exemplar.data.service.impl;

import ai.exemplar.data.fetchers.DataFetcher;
import ai.exemplar.data.service.ScheduledJobsService;
import ai.exemplar.persistence.OAuthTokenRepository;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import java.util.Map;

public class ScheduledJobsServiceImpl implements ScheduledJobsService {

    static final Logger log = Logger.getLogger(ScheduledJobsServiceImpl.class);

    private final OAuthTokenRepository repository;

    private final Map<String, DataFetcher> providers;

    @Inject
    public ScheduledJobsServiceImpl(OAuthTokenRepository repository, Map<String, DataFetcher> providers) {
        this.repository = repository;
        this.providers = providers;
    }

    @Override
    public void invokeCronJob() {
        repository.list().parallelStream()
                .filter(token -> providers.containsKey(token.getProvider()))
                .forEach(token -> providers.get(token.getProvider())
                        .fetchData(token));
    }
}

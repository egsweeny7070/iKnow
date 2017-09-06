package ai.exemplar.callbacks.oauth.service.impl;

import ai.exemplar.callbacks.oauth.providers.OAuthProvider;
import ai.exemplar.persistence.OAuthTokenRepository;
import ai.exemplar.callbacks.oauth.service.ScheduledJobsService;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;

public class ScheduledJobsServiceImpl implements ScheduledJobsService {

    static final Logger log = Logger.getLogger(ScheduledJobsServiceImpl.class);

    private final OAuthTokenRepository repository;

    private final Map<String, OAuthProvider> providers;

    @Inject
    public ScheduledJobsServiceImpl(
            OAuthTokenRepository repository,
            Map<String, OAuthProvider> providers
    ) {
        this.repository = repository;
        this.providers = providers;
    }

    @Override
    public void invokeCronJob() {
        repository.list().parallelStream()
                .filter(token -> token.getExpiration()
                        .isBefore(LocalDateTime.now()
                                .plus(10L, ChronoUnit.MINUTES)))
                .forEach(token -> providers.get(token.getProvider())
                        .renewToken(token));
    }
}

package ai.exemplar.data.fetchers.spotify;

import ai.exemplar.api.spotify.SpotifyApiProvider;
import ai.exemplar.api.spotify.model.AudioFeaturesObject;
import ai.exemplar.api.spotify.model.PlayHistoryObject;
import ai.exemplar.data.fetchers.DataFetcher;
import ai.exemplar.persistence.OAuthTokenRepository;
import ai.exemplar.persistence.SpotifyHistoryRepository;
import ai.exemplar.persistence.dynamodb.schema.spotify.AudioFeaturesDocumentSchema;
import ai.exemplar.persistence.dynamodb.schema.spotify.LinkDocumentSchema;
import ai.exemplar.persistence.dynamodb.schema.spotify.PlayHistoryItemSchema;
import ai.exemplar.persistence.dynamodb.schema.spotify.TrackDocumentSchema;
import ai.exemplar.persistence.model.OAuthToken;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class SpotifyDataFetcher implements DataFetcher {

    static final Logger log = Logger.getLogger(SpotifyDataFetcher.class);

    public static final String PROVIDER_NAME = "spotify";

    private final OAuthTokenRepository tokensRepository;

    private final SpotifyApiProvider api;

    private final SpotifyHistoryRepository historyRepository;

    @Inject
    public SpotifyDataFetcher(OAuthTokenRepository tokensRepository, SpotifyApiProvider api, SpotifyHistoryRepository historyRepository) {
        this.tokensRepository = tokensRepository;
        this.api = api;
        this.historyRepository = historyRepository;
    }

    @Override
    public void fetchData(OAuthToken token) {
        try {
            List<PlayHistoryObject> recentlyPlayed = api.getRecentlyPlayed(
                    token.getToken(),
                    Optional.ofNullable(token.getLastFetched())
                            .orElse(LocalDateTime.now().minus(1L, ChronoUnit.DAYS))
            );

            log.info(String.format("fetched %d spotify history entries for key %s", recentlyPlayed.size(), token.getId()));

            List<PlayHistoryItemSchema> historyItems = recentlyPlayed.stream()
                    .map(playHistoryObject -> new PlayHistoryItemSchema(
                            token.getId(),
                            playHistoryObject.getPlayedAt(),
                            new TrackDocumentSchema(
                                    playHistoryObject.getTrack().getArtists().stream()
                                            .map(linkObject -> new LinkDocumentSchema(
                                                    linkObject.getExternalUrls(),
                                                    linkObject.getHref(),
                                                    linkObject.getId(),
                                                    linkObject.getName(),
                                                    linkObject.getType(),
                                                    linkObject.getUri()
                                            )).collect(Collectors.toList()),
                                    playHistoryObject.getTrack().getAvailableMarkets(),
                                    playHistoryObject.getTrack().getDiscNumber(),
                                    playHistoryObject.getTrack().getDurationMs(),
                                    playHistoryObject.getTrack().getExplicit(),
                                    playHistoryObject.getTrack().getExternalUrls(),
                                    playHistoryObject.getTrack().getHref(),
                                    playHistoryObject.getTrack().getId(),
                                    playHistoryObject.getTrack().getPlayable(),
                                    Optional.ofNullable(playHistoryObject.getTrack().getLinkedFrom())
                                            .map(linkedFrom -> new LinkDocumentSchema(
                                                    linkedFrom.getExternalUrls(),
                                                    linkedFrom.getHref(),
                                                    linkedFrom.getId(),
                                                    linkedFrom.getName(),
                                                    linkedFrom.getType(),
                                                    linkedFrom.getUri()
                                            )).orElse(null),
                                    playHistoryObject.getTrack().getName(),
                                    playHistoryObject.getTrack().getPreviewUrl(),
                                    playHistoryObject.getTrack().getTrackNumber(),
                                    playHistoryObject.getTrack().getType(),
                                    playHistoryObject.getTrack().getUri(),
                                    null
                            ),
                            Optional.ofNullable(playHistoryObject.getContext())
                                    .map(context -> new LinkDocumentSchema(
                                            context.getExternalUrls(),
                                            context.getHref(),
                                            context.getId(),
                                            context.getName(),
                                            context.getType(),
                                            context.getUri()
                                    )).orElse(null)
                    )).collect(Collectors.toList());

            if (!historyItems.isEmpty()) {
                List<String> ids = new ArrayList<>(
                        historyItems.stream()
                                .map(PlayHistoryItemSchema::getTrack)
                                .map(TrackDocumentSchema::getId)
                                .collect(Collectors.toSet())
                );

                Map<String, AudioFeaturesDocumentSchema> features = api
                        .getAudioFeatures(token.getToken(), ids).stream()
                        .filter(Objects::nonNull)
                        .collect(Collectors.toMap(
                                AudioFeaturesObject::getId,
                                audioFeaturesObject -> new AudioFeaturesDocumentSchema(
                                        audioFeaturesObject.getDuration(),
                                        audioFeaturesObject.getMode(),
                                        audioFeaturesObject.getKey(),
                                        audioFeaturesObject.getTimeSignature(),
                                        audioFeaturesObject.getTempo(),
                                        audioFeaturesObject.getAcousticness(),
                                        audioFeaturesObject.getDanceability(),
                                        audioFeaturesObject.getEnergy(),
                                        audioFeaturesObject.getInstrumentalness(),
                                        audioFeaturesObject.getLiveness(),
                                        audioFeaturesObject.getLoudness(),
                                        audioFeaturesObject.getSpeechiness(),
                                        audioFeaturesObject.getValence()
                                )
                        ));

                log.info(String.format("fetched %d spotify track features for key %s", ids.size(), token.getId()));

                historyItems.stream()
                        .map(PlayHistoryItemSchema::getTrack)
                        .forEach(track -> track
                                .setFeatures(features
                                        .get(track.getId())));

                historyRepository.batchSave(historyItems);

                historyItems.stream()
                        .max(Comparator
                                .comparing(PlayHistoryItemSchema::getTimestamp))
                        .ifPresent(lastItem -> tokensRepository
                                .save(new OAuthToken(
                                        token.getId(),
                                        token.getProvider(),
                                        token.getToken(),
                                        token.getRefreshToken(),
                                        token.getCreated(),
                                        token.getUpdated(),
                                        token.getExpiration(),
                                        lastItem.getTimestamp(),
                                        token.getInternalId()
                                ))
                        );
            }

        } catch (Throwable e) {
            log.error(String.format("failed to fetch spotify history for key %s", token.getId()), e);
        }
    }
}

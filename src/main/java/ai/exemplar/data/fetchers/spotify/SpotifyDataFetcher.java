package ai.exemplar.data.fetchers.spotify;

import ai.exemplar.api.spotify.SpotifyApiProvider;
import ai.exemplar.api.spotify.model.AlbumObject;
import ai.exemplar.api.spotify.model.AudioFeaturesObject;
import ai.exemplar.api.spotify.model.PlayHistoryObject;
import ai.exemplar.api.spotify.model.TrackObject;
import ai.exemplar.data.fetchers.DataFetcher;
import ai.exemplar.persistence.OAuthTokenRepository;
import ai.exemplar.persistence.SpotifyHistoryRepository;
import ai.exemplar.persistence.dynamodb.schema.spotify.*;
import ai.exemplar.persistence.model.OAuthToken;
import com.google.common.collect.Lists;
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
                                    null,
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
                                    null,
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

                log.info(String.format("fetched %d spotify track features for key %s", features.size(), token.getId()));

                Map<String, TrackDetails> details = api
                        .getTracks(token.getToken(), ids).stream()
                        .filter(Objects::nonNull)
                        .collect(Collectors.toMap(
                                TrackObject::getId,
                                trackObject -> new TrackDetails(
                                        Optional.ofNullable(trackObject.getAlbum())
                                                .map(AlbumObject::getId).orElse(null),
                                        trackObject.getPopularity()
                                )
                        ));

                log.info(String.format("fetched %d spotify track details for key %s", details.size(), token.getId()));

                Map<String, AlbumDocumentSchema> albums = Lists.partition(new ArrayList<>(
                        details.values().stream()
                                .map(TrackDetails::getAlbumId)
                                .filter(Objects::nonNull)
                                .collect(Collectors.toSet())),
                        20
                ).stream()
                        .flatMap(albumIds -> api
                                .getAlbums(token.getToken(), albumIds).stream()
                                .filter(Objects::nonNull))
                        .collect(Collectors.toMap(
                                AlbumObject::getId,
                                albumObject -> new AlbumDocumentSchema(
                                        albumObject.getAlbumType(),
                                        albumObject.getArtists().stream()
                                                .map(linkObject -> new LinkDocumentSchema(
                                                        linkObject.getExternalUrls(),
                                                        linkObject.getHref(),
                                                        linkObject.getId(),
                                                        linkObject.getName(),
                                                        linkObject.getType(),
                                                        linkObject.getUri()
                                                )).collect(Collectors.toList()),
                                        albumObject.getMarkets(),
                                        albumObject.getGenres(),
                                        albumObject.getHref(),
                                        albumObject.getId(),
                                        albumObject.getImages().stream()
                                                .map(imageObject -> new ImageDocumentSchema(
                                                        imageObject.getHeight(),
                                                        imageObject.getWidth(),
                                                        imageObject.getUrl()
                                                )).collect(Collectors.toList()),
                                        albumObject.getLabel(),
                                        albumObject.getName(),
                                        albumObject.getPopularity(),
                                        albumObject.getReleaseDate(),
                                        albumObject.getReleaseDatePrecision(),
                                        albumObject.getUri()
                                )
                        ));

                log.info(String.format("fetched %d spotify albums for key %s", albums.size(), token.getId()));

                historyItems.stream()
                        .map(PlayHistoryItemSchema::getTrack)
                        .forEach(track -> {
                            track.setFeatures(Optional.ofNullable(features
                                    .get(track.getId())).orElse(null));

                            track.setPopularity(Optional.ofNullable(details
                                    .get(track.getId()))
                                    .map(TrackDetails::getPopularity).orElse(null));

                            track.setAlbum(Optional.ofNullable(details.get(track.getId()))
                                    .map(TrackDetails::getAlbumId)
                                    .flatMap(id -> Optional
                                            .ofNullable(albums.get(id)))
                                    .orElse(null));
                        });

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

    private static class TrackDetails {

        private final String albumId;

        private final Integer popularity;

        public TrackDetails(String albumId, Integer popularity) {
            this.albumId = albumId;
            this.popularity = popularity;
        }

        public String getAlbumId() {
            return albumId;
        }

        public Integer getPopularity() {
            return popularity;
        }
    }
}

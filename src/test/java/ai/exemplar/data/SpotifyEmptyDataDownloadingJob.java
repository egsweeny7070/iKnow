package ai.exemplar.data;

import ai.exemplar.api.spotify.model.AlbumObject;
import ai.exemplar.api.spotify.model.AudioFeaturesObject;
import ai.exemplar.api.spotify.model.TrackObject;
import ai.exemplar.persistence.dynamodb.schema.spotify.*;
import ai.exemplar.utils.test.DaggerExemplarServicesComponent;
import ai.exemplar.utils.test.ExemplarServicesComponent;
import com.google.common.collect.Lists;
import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

@Ignore
public class SpotifyEmptyDataDownloadingJob {

    static final Logger log = Logger.getLogger(SpotifyEmptyDataDownloadingJob.class);

    @Test
    public void spotifyEmptyFeaturesDownloadingJob() {
        ExemplarServicesComponent service = DaggerExemplarServicesComponent.create();

        List<PlayHistoryItemSchema> history = service
                .spotifyHistoryRepository()
                .list("Jesus");

        Map<String, AudioFeaturesDocumentSchema> features = Lists.partition(new ArrayList<>(
                history.stream()
                        .map(PlayHistoryItemSchema::getTrack)
                        .filter(track -> track.getFeatures() == null)
                        .map(TrackDocumentSchema::getId)
                        .collect(Collectors.toSet())),
                100
        ).stream()
                .flatMap(ids -> {
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                    }

                    return service
                            .spotifyApiProvider()
                            .getAudioFeatures(
                                    service.oAuthTokenRepository()
                                            .get("Jesus", "spotify")
                                            .getToken(),
                                    ids
                            )
                            .stream()
                            .filter(Objects::nonNull);
                })
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

        log.info(String.format("fetched %d track features", features.size()));

        Map<String, TrackDetails> details = Lists.partition(new ArrayList<>(
                history.stream()
                        .map(PlayHistoryItemSchema::getTrack)
                        .filter(track -> track.getPopularity() == null || track.getAlbum() == null)
                        .map(TrackDocumentSchema::getId)
                        .collect(Collectors.toSet())),
                50
        ).stream()
                .flatMap(ids -> {
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                    }

                    return service
                            .spotifyApiProvider()
                            .getTracks(
                                    service.oAuthTokenRepository()
                                            .get("Jesus", "spotify")
                                            .getToken(),
                                    ids
                            )
                            .stream()
                            .filter(Objects::nonNull);
                })
                .collect(Collectors.toMap(
                        TrackObject::getId,
                        trackObject -> new TrackDetails(
                                Optional.ofNullable(trackObject.getAlbum())
                                        .map(AlbumObject::getId).orElse(null),
                                trackObject.getPopularity()
                        )
                ));

        log.info(String.format("fetched %d track details", details.size()));

        Map<String, AlbumDocumentSchema> albums = Lists.partition(new ArrayList<>(
                details.values().stream()
                        .map(TrackDetails::getAlbumId)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toSet())),
                20
        ).stream()
                .flatMap(ids -> {
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                    }

                    return service
                            .spotifyApiProvider()
                            .getAlbums(
                                    service.oAuthTokenRepository()
                                            .get("Jesus", "spotify")
                                            .getToken(),
                                    ids
                            )
                            .stream()
                            .filter(Objects::nonNull);
                })
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

        log.info(String.format("fetched %d albums", albums.size()));

        history.stream()
                .map(PlayHistoryItemSchema::getTrack)
                .forEach(track -> {
                    if (track.getFeatures() == null) {
                        track.setFeatures(Optional.ofNullable(features
                                .get(track.getId())).orElse(null));
                    }

                    if (track.getPopularity() == null) {
                        track.setPopularity(Optional.ofNullable(details
                                .get(track.getId()))
                                .map(TrackDetails::getPopularity).orElse(null));
                    }

                    if (track.getAlbum() == null) {
                        track.setAlbum(Optional.ofNullable(details.get(track.getId()))
                                .map(TrackDetails::getAlbumId)
                                .flatMap(id -> Optional
                                        .ofNullable(albums.get(id)))
                                .orElse(null));
                    }
                });

        service.spotifyHistoryRepository()
                .batchSave(history);
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

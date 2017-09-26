package ai.exemplar.data;

import ai.exemplar.api.spotify.model.AudioFeaturesObject;
import ai.exemplar.persistence.dynamodb.schema.spotify.AudioFeaturesDocumentSchema;
import ai.exemplar.persistence.dynamodb.schema.spotify.PlayHistoryItemSchema;
import ai.exemplar.persistence.dynamodb.schema.spotify.TrackDocumentSchema;
import ai.exemplar.utils.DaggerExemplarServicesComponent;
import ai.exemplar.utils.ExemplarServicesComponent;
import com.google.common.collect.Lists;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Ignore
public class SpotifyEmptyFeaturesDownloadingJob {

    @Test
    public void spotifyEmptyFeaturesDownloadingJob() {
        ExemplarServicesComponent service = DaggerExemplarServicesComponent.create();

        List<PlayHistoryItemSchema> history = service
                .spotifyHistoryRepository().list("Jesus");

        Map<String, AudioFeaturesDocumentSchema> features = Lists.partition(new ArrayList<>(
                history.stream()
                        .map(PlayHistoryItemSchema::getTrack)
                        .filter(track -> track.getFeatures() == null)
                        .map(TrackDocumentSchema::getId)
                        .collect(Collectors.toSet())),
                100
        ).stream()
                .flatMap(ids -> service
                        .spotifyApiProvider().getAudioFeatures(service
                                        .oAuthTokenRepository().get("Jesus", "spotify").getToken(),
                                ids
                        ).stream()
                        .filter(Objects::nonNull))
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

        history.stream()
                .map(PlayHistoryItemSchema::getTrack)
                .forEach(track -> track
                        .setFeatures(features
                                .get(track.getId())));

        service.spotifyHistoryRepository()
                .batchSave(history);
    }
}

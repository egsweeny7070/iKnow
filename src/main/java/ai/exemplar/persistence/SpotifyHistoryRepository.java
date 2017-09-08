package ai.exemplar.persistence;

import ai.exemplar.persistence.dynamodb.schema.spotify.PlayHistoryItemSchema;

import java.util.List;

public interface SpotifyHistoryRepository {

    void batchSave(List<PlayHistoryItemSchema> batch);
}

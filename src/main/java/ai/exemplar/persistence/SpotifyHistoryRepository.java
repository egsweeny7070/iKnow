package ai.exemplar.persistence;

import ai.exemplar.persistence.dynamodb.schema.spotify.PlayHistoryItemSchema;

import java.util.List;

public interface SpotifyHistoryRepository {

    List<PlayHistoryItemSchema> list(String key);

    void batchSave(List<PlayHistoryItemSchema> batch);

    List<PlayHistoryItemSchema> scan();
}

package ai.exemplar.persistence;

import ai.exemplar.persistence.dynamodb.schema.spotify.PlayHistoryItemSchema;

import java.time.LocalDateTime;
import java.util.List;

public interface SpotifyHistoryRepository {

    List<PlayHistoryItemSchema> list(String key);

    List<PlayHistoryItemSchema> list(String key, LocalDateTime timestampFrom, LocalDateTime timestampTo);

    void batchSave(List<PlayHistoryItemSchema> batch);

    List<PlayHistoryItemSchema> scan();

    List<PlayHistoryItemSchema> recent(String key, int limit);
}

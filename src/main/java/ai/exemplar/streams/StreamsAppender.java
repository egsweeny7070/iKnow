package ai.exemplar.streams;

import ai.exemplar.persistence.dynamodb.schema.spotify.PlayHistoryItemSchema;
import ai.exemplar.persistence.model.SquarePayment;

public interface StreamsAppender {

    void appendPayment(SquarePayment payment);

    void appendTrack(PlayHistoryItemSchema track);
}

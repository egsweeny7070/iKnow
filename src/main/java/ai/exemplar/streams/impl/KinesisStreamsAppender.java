package ai.exemplar.streams.impl;

import ai.exemplar.persistence.dynamodb.schema.spotify.PlayHistoryItemSchema;
import ai.exemplar.persistence.model.SquarePayment;
import ai.exemplar.streams.StreamsAppender;
import ai.exemplar.streams.kinesis.KinesisStreamName;
import ai.exemplar.streams.values.StreamEvent;
import ai.exemplar.utils.json.GsonFabric;
import com.amazonaws.AmazonClientException;
import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.model.PutRecordRequest;
import com.amazonaws.services.kinesis.model.ResourceNotFoundException;
import com.google.gson.Gson;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import java.nio.ByteBuffer;

public class KinesisStreamsAppender implements StreamsAppender {

    static final Logger log = Logger.getLogger(KinesisStreamsAppender.class);

    private final String streamName;

    private final AmazonKinesis kinesis;

    private final Gson gson = GsonFabric.simplified();

    @Inject
    public KinesisStreamsAppender(@KinesisStreamName String streamName, AmazonKinesis kinesis) {
        this.streamName = streamName;
        this.kinesis = kinesis;

        createIfNotExists(streamName);
    }

    private void createIfNotExists(String streamName) {
        try {
            String status = kinesis.describeStream(streamName)
                    .getStreamDescription().getStreamStatus();

            log.debug(String.format("stream %s status %s", streamName, status));

        } catch (ResourceNotFoundException e) {
            try {
                log.info(String.format("creating stream %s", streamName));

                kinesis.createStream(
                        streamName,
                        1
                );

            } catch (AmazonClientException ex) {
                log.error(String.format("stream %s creation exception", streamName), ex);

                throw new RuntimeException(ex);
            }
        }
    }

    private void write(StreamEvent event) {
        PutRecordRequest putRecord = new PutRecordRequest();

        putRecord.setStreamName(streamName);
        putRecord.setPartitionKey(event.getKey());

        String jsonBody = gson.toJson(event);

        log.debug(String.format("publishing entry with key %s to stream %s", event.getKey(), streamName));

        try {
            putRecord.setData(ByteBuffer
                    .wrap(jsonBody.getBytes("UTF-8")));

            kinesis.putRecord(putRecord);

        } catch (AmazonClientException e) {
            log.error(String.format("publishing key %s to stream %s exception", event.getKey(), streamName), e);

        } catch (Exception e) {
            log.error(String.format("publishing key %s to stream %s runtime exception", event.getKey(), streamName), e);

            throw new RuntimeException(e);
        }
    }

    @Override
    public void appendPayment(SquarePayment payment) {
        write(new StreamEvent(payment));
    }

    @Override
    public void appendTrack(PlayHistoryItemSchema track) {
        write(new StreamEvent(track));
    }
}

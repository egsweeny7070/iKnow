package ai.exemplar.upload;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.KinesisEvent;
import org.apache.log4j.Logger;

import java.util.stream.Collectors;

public class KinesisEventSourceHandler implements RequestHandler<KinesisEvent, Void> {

    static final Logger log = Logger.getLogger(KinesisEventSourceHandler.class);

    @Override
    public Void handleRequest(KinesisEvent input, Context context) {
        try {
            DaggerExemplarUploadComponent
                    .create()
                    .recordUploadService()
                    .upload(input
                            .getRecords().stream()
                            .map(KinesisEvent.KinesisEventRecord::getKinesis)
                            .map(KinesisEvent.Record::getData)
                            .collect(Collectors.toList())
                    );

        } catch (Throwable e) {
            log.error("kinesis event source processing failed", e);

            throw new RuntimeException(e);
        }

        return null;
    }
}

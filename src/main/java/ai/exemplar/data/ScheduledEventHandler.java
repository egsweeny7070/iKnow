package ai.exemplar.data;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import org.apache.log4j.Logger;

public class ScheduledEventHandler implements RequestHandler<SNSEvent, Object> {

    static final Logger log = Logger.getLogger(ScheduledEventHandler.class);

    @Override
    public Object handleRequest(SNSEvent input, Context context) {
        try {
            DaggerExemplarDataComponent
                    .create()
                    .scheduledJobsService()
                    .invokeCronJob();

        } catch (Throwable e) {
            log.error("scheduled event processing failed", e);
        }

        return null;
    }
}

package ai.exemplar.upload.service.impl;

import ai.exemplar.persistence.PaymentsAnalyticsRepository;
import ai.exemplar.persistence.TracksAnalyticsRepository;
import ai.exemplar.persistence.dynamodb.schema.analytics.TrackFeatureAnalyticsDocumentSchema;
import ai.exemplar.persistence.model.PaymentsAnalyticsItem;
import ai.exemplar.persistence.model.TracksAnalyticsItem;
import ai.exemplar.upload.service.RecordUploadService;
import ai.exemplar.upload.service.values.UploadStreamRecord;
import ai.exemplar.utils.json.GsonFabric;
import com.amazonaws.util.Base64;
import com.amazonaws.util.StringUtils;
import com.google.gson.Gson;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.stream.Collectors;

public class RecordUploadServiceImpl implements RecordUploadService {

    static final Logger log = Logger.getLogger(RecordUploadServiceImpl.class);

    private final PaymentsAnalyticsRepository paymentsAnalyticsRepository;

    private final TracksAnalyticsRepository tracksAnalyticsRepository;

    private final Gson gson = GsonFabric.simplified();

    @Inject
    public RecordUploadServiceImpl(PaymentsAnalyticsRepository paymentsAnalyticsRepository, TracksAnalyticsRepository tracksAnalyticsRepository) {
        this.paymentsAnalyticsRepository = paymentsAnalyticsRepository;
        this.tracksAnalyticsRepository = tracksAnalyticsRepository;
    }

    @Override
    public void upload(List<ByteBuffer> streamRecords) {
        log.info(String.format("received %d stream records",
                streamRecords.size()));

        List<UploadStreamRecord> records = streamRecords.stream()
                .map(ByteBuffer::array)
                .map(content -> new String(
                        content,
                        StringUtils.UTF8
                ))
                .peek(log::info)
                .map(jsonContent -> gson.fromJson(
                        jsonContent,
                        UploadStreamRecord.class
                ))
                .collect(Collectors.toList());

        List<PaymentsAnalyticsItem> paymentsAnalyticsItems = records.stream()
                .filter(record -> record
                        .getTotalPaymentsCount() != null)
                .map(record -> new PaymentsAnalyticsItem(
                        record.getLocation(),
                        record.getIngest(),
                        record.getTimestamp(),
                        record.getTotalPaymentsCount(),
                        record.getTotalItemsCount(),
                        record.getTotalDiscountPercent(),
                        record.getCollectedAmount()
                ))
                .collect(Collectors.toList());

        List<TracksAnalyticsItem> tracksAnalyticsItems = records.stream()
                .filter(record -> record
                        .getTracksCount() != null)
                .map(record -> new TracksAnalyticsItem(
                        record.getLocation(),
                        record.getIngest(),
                        record.getTimestamp(),
                        record.getTracksCount(),
                        new TrackFeatureAnalyticsDocumentSchema(
                                record.getMinAcousticness(),
                                record.getMaxAcousticness(),
                                record.getSumAcousticness(),
                                record.getStdAcousticness()
                        ),
                        new TrackFeatureAnalyticsDocumentSchema(
                                record.getMinDanceability(),
                                record.getMaxDanceability(),
                                record.getSumDanceability(),
                                record.getStdDanceability()
                        ),
                        new TrackFeatureAnalyticsDocumentSchema(
                                record.getMinEnergy(),
                                record.getMaxEnergy(),
                                record.getSumEnergy(),
                                record.getStdEnergy()
                        ),
                        new TrackFeatureAnalyticsDocumentSchema(
                                record.getMinInstrumentalness(),
                                record.getMaxInstrumentalness(),
                                record.getSumInstrumentalness(),
                                record.getStdInstrumentalness()
                        ),
                        new TrackFeatureAnalyticsDocumentSchema(
                                record.getMinLiveness(),
                                record.getMaxLiveness(),
                                record.getSumLiveness(),
                                record.getStdLiveness()
                        ),
                        new TrackFeatureAnalyticsDocumentSchema(
                                record.getMinLoudness(),
                                record.getMaxLoudness(),
                                record.getSumLoudness(),
                                record.getStdLoudness()
                        ),
                        new TrackFeatureAnalyticsDocumentSchema(
                                record.getMinSpeechiness(),
                                record.getMaxSpeechiness(),
                                record.getSumSpeechiness(),
                                record.getStdSpeechiness()
                        ),
                        new TrackFeatureAnalyticsDocumentSchema(
                                record.getMinValence(),
                                record.getMaxValence(),
                                record.getSumValence(),
                                record.getStdValence()
                        ),
                        new TrackFeatureAnalyticsDocumentSchema(
                                record.getMinDuration(),
                                record.getMaxDuration(),
                                record.getSumDuration(),
                                record.getStdDuration()
                        ),
                        new TrackFeatureAnalyticsDocumentSchema(
                                record.getMinTempo(),
                                record.getMaxTempo(),
                                record.getSumTempo(),
                                record.getStdTempo()
                        )
                ))
                .collect(Collectors.toList());

        if (!paymentsAnalyticsItems.isEmpty()) {
            log.info(String.format("writing %d payments analytics items",
                    paymentsAnalyticsItems.size()));

            paymentsAnalyticsRepository
                    .save(paymentsAnalyticsItems);
        }

        if (!tracksAnalyticsItems.isEmpty()) {
            log.info(String.format("writing %d tracks analytics items",
                    tracksAnalyticsItems.size()));

            tracksAnalyticsRepository
                    .save(tracksAnalyticsItems);
        }
    }
}

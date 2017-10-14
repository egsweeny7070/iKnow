package ai.exemplar.persistence.dynamodb;

import ai.exemplar.persistence.TracksAnalyticsRepository;
import ai.exemplar.persistence.dynamodb.schema.analytics.TracksAnalyticsItemSchema;
import ai.exemplar.persistence.model.TracksAnalyticsItem;
import ai.exemplar.utils.dynamodb.CreateTableHelper;
import ai.exemplar.utils.dynamodb.converters.LocalDateTimeTypeConverter;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class DynamoDBTracksAnalyticsRepository implements TracksAnalyticsRepository {

    static final Logger log = Logger.getLogger(DynamoDBPaymentsAnalyticsRepository.class);

    private final DynamoDBTableMapper<TracksAnalyticsItemSchema, String, String> tracksAnalytics;

    @Inject
    public DynamoDBTracksAnalyticsRepository(AmazonDynamoDB amazonDynamoDB) {
        final DynamoDBMapper mapper = new DynamoDBMapper(
                amazonDynamoDB,
                new DynamoDBMapperConfig.Builder()
                        .withConversionSchema(ConversionSchemas.V2)
                        .withTableNameOverride(new DynamoDBMapperConfig
                                .TableNameOverride("TracksBasicAnalytics"))
                        .build()
        );

        CreateTableHelper.createTableIfNotExists(
                amazonDynamoDB,
                mapper,
                TracksAnalyticsItemSchema.class,
                new ProvisionedThroughput(5L, 5L)
        );

        this.tracksAnalytics = mapper.newTableMapper(TracksAnalyticsItemSchema.class);
    }

    @Override
    public void save(List<TracksAnalyticsItem> batch) {
        List<DynamoDBMapper.FailedBatch> failed = tracksAnalytics.batchSave(batch.stream()
                .map(TracksAnalyticsItemSchema::fromDomain)
                .collect(Collectors.toList()));

        if (!failed.isEmpty()) {
            failed.stream()
                    .map(DynamoDBMapper.FailedBatch::getException)
                    .forEach(log::error);

            throw new RuntimeException(failed.stream()
                    .map(DynamoDBMapper.FailedBatch::getException)
                    .findAny().get());
        }
    }

    @Override
    public List<TracksAnalyticsItem> query(String location, LocalDateTime timestampFrom, LocalDateTime timestampTo) {
        return tracksAnalytics.query(
                new DynamoDBQueryExpression<TracksAnalyticsItemSchema>()
                        .withIndexName("LocationTracksIndex")
                        .withHashKeyValues(TracksAnalyticsItemSchema
                                .partitionKey(location))
                        .withRangeKeyCondition("timestamp", new Condition()
                                .withComparisonOperator(ComparisonOperator.BETWEEN)
                                .withAttributeValueList(
                                        new AttributeValue().withS(new LocalDateTimeTypeConverter()
                                                .convert(timestampFrom)),
                                        new AttributeValue().withS(new LocalDateTimeTypeConverter()
                                                .convert(timestampTo))
                                ))
                        .withConsistentRead(false)
        ).stream()
                .map(TracksAnalyticsItemSchema::toDomain)
                .collect(Collectors.toList());
    }
}

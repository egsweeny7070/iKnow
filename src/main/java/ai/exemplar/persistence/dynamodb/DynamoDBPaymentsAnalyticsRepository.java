package ai.exemplar.persistence.dynamodb;

import ai.exemplar.persistence.PaymentsAnalyticsRepository;
import ai.exemplar.persistence.dynamodb.schema.analytics.PaymentsAnalyticsItemSchema;
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

public class DynamoDBPaymentsAnalyticsRepository implements PaymentsAnalyticsRepository {

    static final Logger log = Logger.getLogger(DynamoDBPaymentsAnalyticsRepository.class);

    private final DynamoDBTableMapper<PaymentsAnalyticsItemSchema, String, String> paymentsAnalytics;

    @Inject
    public DynamoDBPaymentsAnalyticsRepository(AmazonDynamoDB amazonDynamoDB) {
        final DynamoDBMapper mapper = new DynamoDBMapper(
                amazonDynamoDB,
                new DynamoDBMapperConfig.Builder()
                        .withConversionSchema(ConversionSchemas.V2)
                        .withTableNameOverride(new DynamoDBMapperConfig
                                .TableNameOverride("PaymentsBasicAnalytics"))
                        .build()
        );

        CreateTableHelper.createTableIfNotExists(
                amazonDynamoDB,
                mapper,
                PaymentsAnalyticsItemSchema.class,
                new ProvisionedThroughput(5L, 5L)
        );

        this.paymentsAnalytics = mapper.newTableMapper(PaymentsAnalyticsItemSchema.class);
    }

    @Override
    public void save(List<PaymentsAnalyticsItemSchema> batch) {
        List<DynamoDBMapper.FailedBatch> failed = paymentsAnalytics.batchSave(batch);

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
    public List<PaymentsAnalyticsItemSchema> query(String location, LocalDateTime timestampFrom, LocalDateTime timestampTo) {
        return paymentsAnalytics.query(
                new DynamoDBQueryExpression<PaymentsAnalyticsItemSchema>()
                        .withHashKeyValues(PaymentsAnalyticsItemSchema
                                .partitionKey(location))
                        .withRangeKeyCondition("rowTime", new Condition()
                                .withComparisonOperator(ComparisonOperator.BETWEEN)
                                .withAttributeValueList(
                                        new AttributeValue().withS(new LocalDateTimeTypeConverter()
                                                .convert(timestampFrom)),
                                        new AttributeValue().withS(new LocalDateTimeTypeConverter()
                                                .convert(timestampTo))
                                ))
                        .withConsistentRead(true)
        );
    }
}

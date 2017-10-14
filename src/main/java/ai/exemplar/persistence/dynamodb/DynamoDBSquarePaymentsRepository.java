package ai.exemplar.persistence.dynamodb;

import ai.exemplar.persistence.SquarePaymentsRepository;
import ai.exemplar.persistence.dynamodb.schema.square.PaymentSchema;
import ai.exemplar.persistence.model.SquarePayment;
import ai.exemplar.utils.dynamodb.CreateTableHelper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.ConversionSchemas;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTableMapper;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

public class DynamoDBSquarePaymentsRepository implements SquarePaymentsRepository {

    static final Logger log = Logger.getLogger(DynamoDBSquarePaymentsRepository.class);

    private final DynamoDBTableMapper<PaymentSchema, String, String> squarePayments;

    @Inject
    public DynamoDBSquarePaymentsRepository(AmazonDynamoDB amazonDynamoDB) {
        final DynamoDBMapper mapper = new DynamoDBMapper(
                amazonDynamoDB,
                new DynamoDBMapperConfig.Builder()
                        .withConversionSchema(ConversionSchemas.V2)
                        .withTableNameOverride(new DynamoDBMapperConfig
                                .TableNameOverride("SquarePaymentHistory"))
                        .build()
        );

        CreateTableHelper.createTableIfNotExists(
                amazonDynamoDB,
                mapper,
                PaymentSchema.class,
                new ProvisionedThroughput(5L, 5L)
        );

        this.squarePayments = mapper.newTableMapper(PaymentSchema.class);
    }

    @Override
    public void batchSave(List<SquarePayment> batch) {
        List<DynamoDBMapper.FailedBatch> failed = squarePayments
                .batchSave(batch.stream()
                        .map(PaymentSchema::fromModel)
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
}

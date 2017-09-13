package ai.exemplar.persistence.dynamodb;

import ai.exemplar.persistence.SquareLocationRepository;
import ai.exemplar.persistence.dynamodb.schema.square.LocationSchema;
import ai.exemplar.utils.dynamodb.CreateTableHelper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import java.util.List;

public class DynamoDBSquareLocationsRepository implements SquareLocationRepository {

    static final Logger log = Logger.getLogger(DynamoDBSquareLocationsRepository.class);

    private final DynamoDBTableMapper<LocationSchema, String, String> squareLocations;

    @Inject
    public DynamoDBSquareLocationsRepository(AmazonDynamoDB amazonDynamoDB) {
        final DynamoDBMapper mapper = new DynamoDBMapper(
                amazonDynamoDB,
                new DynamoDBMapperConfig.Builder()
                        .withConversionSchema(ConversionSchemas.V2)
                        .withTableNameOverride(new DynamoDBMapperConfig
                                .TableNameOverride("SquareLocation"))
                        .build()
        );

        CreateTableHelper.createTableIfNotExists(
                amazonDynamoDB,
                mapper,
                LocationSchema.class,
                new ProvisionedThroughput(1L, 1L)
        );

        this.squareLocations = mapper.newTableMapper(LocationSchema.class);
    }

    @Override
    public List<LocationSchema> list(String account) {
        return squareLocations.query(
                new DynamoDBQueryExpression<LocationSchema>()
                        .withHashKeyValues(LocationSchema
                                .hashKey(account))
                        .withConsistentRead(true)
        );
    }

    @Override
    public void save(LocationSchema location) {
        squareLocations.save(location);
    }

    @Override
    public void batchSave(List<LocationSchema> batch) {
        List<DynamoDBMapper.FailedBatch> failed = squareLocations
                .batchSave(batch);

        if (!failed.isEmpty()) {
            failed.stream()
                    .map(DynamoDBMapper.FailedBatch::getException)
                    .forEach(e -> log.error("square location save exception: ", e));

            throw new RuntimeException("batch save left unprocessed items");
        }
    }
}

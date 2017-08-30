package ai.exemplar.utils.dynamodb;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.*;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public class CreateTableHelper {

    /**
     * Creates table (with global secondary indexes) if it is not exists.
     * Sets for the table provisioned throughput value specified.
     * For global secondary indexes set the throughput specified in the map (based on the index name).
     * For the indexes not mentioned in the map sets the same value as for the entire table.
     * For all indexes mentioned in the particular map sets the specified projection value.
     * Sets ProjectionType.ALL for all global secondary indexes not mentioned in the map.
     * @param db dynamodb client.
     * @param mapper mapper instance.
     * @param tableAnnotatedClass table class.
     * @param throughput initial provisioned throughput value for the table and each gsi.
     * @param indexThroughput throughput value for the particular global secondary index.
     * @param indexProjection projection value for the particular global secondary index.
     * @return true if the table were created.
     */
    public static boolean createTableIfNotExists(
            AmazonDynamoDB db,
            DynamoDBMapper mapper,
            Class tableAnnotatedClass,
            ProvisionedThroughput throughput,
            Map<String, ProvisionedThroughput> indexThroughput,
            Map<String, Projection> indexProjection
    ) {
        final CreateTableRequest request = mapper.generateCreateTableRequest(tableAnnotatedClass);
        request.setProvisionedThroughput(throughput);
        Optional.ofNullable(request.getGlobalSecondaryIndexes()).ifPresent(
                globalSecondaryIndices -> globalSecondaryIndices
                        .forEach(gsi -> {
                            gsi.setProvisionedThroughput(
                                    indexThroughput.getOrDefault(
                                            gsi.getIndexName(), throughput));
                            gsi.setProjection(
                                    indexProjection.getOrDefault(
                                            gsi.getIndexName(), new Projection()
                                                    .withProjectionType(ProjectionType.ALL)));
                        })
        );
        try {
            db.createTable(request);
        } catch (final ResourceInUseException e) {
            return false;
        }
        return true;
    }

    /**
     * Creates table (with global secondary indexes) if it is not exists.
     * Sets for the table and all its global secondary indexes the same
     * throughput value specified.
     * Sets for all global secondary indexes ProjectionType.ALL
     * @param db dynamodb client.
     * @param mapper mapper instance.
     * @param tableAnnotatedClass table class.
     * @param throughput initial provisioned throughput value for the table and each gsi.
     * @return true if the table were created.
     */
    public static boolean createTableIfNotExists(
            AmazonDynamoDB db,
            DynamoDBMapper mapper,
            Class tableAnnotatedClass,
            ProvisionedThroughput throughput
    ) {
        return createTableIfNotExists(
                db, mapper, tableAnnotatedClass,
                throughput, Collections.emptyMap(), Collections.emptyMap()
        );
    }
}

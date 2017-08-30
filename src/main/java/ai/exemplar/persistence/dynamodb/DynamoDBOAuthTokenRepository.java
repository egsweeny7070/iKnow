package ai.exemplar.persistence.dynamodb;

import ai.exemplar.persistence.OAuthTokenRepository;
import ai.exemplar.persistence.dynamodb.schema.OAuthTokenSchema;
import ai.exemplar.persistence.model.OAuthToken;
import ai.exemplar.utils.dynamodb.CreateTableHelper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

public class DynamoDBOAuthTokenRepository implements OAuthTokenRepository {

    static final Logger log = Logger.getLogger(DynamoDBOAuthTokenRepository.class);

    private final DynamoDBTableMapper<OAuthTokenSchema, String, ?> tokens;

    @Inject
    public DynamoDBOAuthTokenRepository(AmazonDynamoDB amazonDynamoDB) {
        final DynamoDBMapper mapper = new DynamoDBMapper(
                amazonDynamoDB,
                new DynamoDBMapperConfig.Builder()
                        .withConversionSchema(ConversionSchemas.V2)
                        .withTableNameOverride(new DynamoDBMapperConfig.TableNameOverride(
                                "OAuthToken"
                        ))
                        .build()
        );

        CreateTableHelper.createTableIfNotExists(
                amazonDynamoDB,
                mapper,
                OAuthTokenSchema.class,
                new ProvisionedThroughput(1L, 1L)
        );

        this.tokens = mapper.newTableMapper(OAuthTokenSchema.class);
    }

    @Override
    public OAuthToken get(String username, String provider) {
        return tokens.load(OAuthTokenSchema
                .primaryKey(username, provider).getKey())
                .toModel();
    }

    @Override
    public void save(OAuthToken token) {
        tokens.save(OAuthTokenSchema.fromModel(token));
    }

    @Override
    public void delete(String username, String provider) {
        tokens.delete(OAuthTokenSchema
                .primaryKey(username, provider));
    }

    @Override
    public List<OAuthToken> list() {
        return tokens.scan(
                new DynamoDBScanExpression()
                        .withConsistentRead(true)
        ).stream()
                .map(OAuthTokenSchema::toModel)
                .collect(Collectors.toList());
    }
}

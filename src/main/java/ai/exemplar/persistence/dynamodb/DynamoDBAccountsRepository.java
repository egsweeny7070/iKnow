package ai.exemplar.persistence.dynamodb;

import ai.exemplar.persistence.AccountsRepository;
import ai.exemplar.persistence.dynamodb.schema.AccountSchema;
import ai.exemplar.utils.dynamodb.CreateTableHelper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.ConversionSchemas;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTableMapper;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import org.apache.log4j.Logger;

import javax.inject.Inject;

public class DynamoDBAccountsRepository implements AccountsRepository {

    static final Logger log = Logger.getLogger(DynamoDBOAuthTokenRepository.class);

    private final DynamoDBTableMapper<AccountSchema, String, ?> accounts;

    @Inject
    public DynamoDBAccountsRepository(AmazonDynamoDB amazonDynamoDB) {
        final DynamoDBMapper mapper = new DynamoDBMapper(
                amazonDynamoDB,
                new DynamoDBMapperConfig.Builder()
                        .withConversionSchema(ConversionSchemas.V2)
                        .withTableNameOverride(new DynamoDBMapperConfig
                                .TableNameOverride("Account"))
                        .build()
        );

        CreateTableHelper.createTableIfNotExists(
                amazonDynamoDB,
                mapper,
                AccountSchema.class,
                new ProvisionedThroughput(5L, 1L)
        );

        this.accounts = mapper.newTableMapper(AccountSchema.class);
    }

    @Override
    public void save(AccountSchema account) {
        accounts.save(account);
    }

    @Override
    public AccountSchema get(String id) {
        return accounts.load(id);
    }
}

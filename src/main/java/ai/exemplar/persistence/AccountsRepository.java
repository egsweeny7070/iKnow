package ai.exemplar.persistence;

import ai.exemplar.persistence.dynamodb.schema.AccountSchema;

public interface AccountsRepository {

    void save(AccountSchema account);

    AccountSchema get(String id);
}

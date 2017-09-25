package ai.exemplar.common;

import ai.exemplar.persistence.dynamodb.schema.square.LocationSchema;
import ai.exemplar.persistence.model.OAuthToken;

import java.util.List;

public interface LocationsService {

    List<LocationSchema> locations(OAuthToken token);

    LocationSchema get(String account, String id);

    void save(LocationSchema location);
}

package ai.exemplar.utils.dynamodb.converters;

import ai.exemplar.utils.json.GsonFabric;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Collections;
import java.util.List;

public class StringListTypeConverter implements DynamoDBTypeConverter<String, List<String>> {

    private static final Gson gson = GsonFabric.gson();

    @Override
    public String convert(List<String> object) {
        return object == null ? null
                : gson.toJson(object);
    }

    @Override
    public List<String> unconvert(String object) {
        return object == null ? Collections.emptyList()
                : gson.fromJson(object, new TypeToken<List<String>>() {}.getType());
    }
}

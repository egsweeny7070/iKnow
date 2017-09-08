package ai.exemplar.utils.dynamodb.converters;

import ai.exemplar.utils.json.GsonFabric;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Map;

public class StringMapTypeConverter implements DynamoDBTypeConverter<String, Map<String, String>> {

    private static final Gson gson = GsonFabric.gson();

    @Override
    public String convert(Map<String, String> object) {
        return object == null ? null
                : gson.toJson(object);
    }

    @Override
    public Map<String, String> unconvert(String object) {
        return object == null ? null
                : gson.fromJson(object, new TypeToken<Map<String, String>>() {}.getType());
    }
}

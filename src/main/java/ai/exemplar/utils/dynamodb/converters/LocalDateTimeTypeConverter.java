package ai.exemplar.utils.dynamodb.converters;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeTypeConverter implements DynamoDBTypeConverter<String, LocalDateTime> {
    @Override
    public String convert(final LocalDateTime time) {
        return time == null ? null
                : time.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    @Override
    public LocalDateTime unconvert(final String stringValue) {
        return stringValue == null ? null
                : LocalDateTime.parse(stringValue, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}

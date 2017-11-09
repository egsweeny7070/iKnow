package ai.exemplar.utils.json.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalTimeTypeAdapter extends TypeAdapter<LocalTime> {

    @Override
    public void write(JsonWriter out, LocalTime value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            out.value(
                    value.format(
                            DateTimeFormatter.ISO_LOCAL_TIME
                    )
            );
        }
    }

    @Override
    public LocalTime read(JsonReader in) throws IOException {
        return LocalTime.parse(
                in.nextString(),
                DateTimeFormatter.ISO_LOCAL_TIME
        );
    }
}

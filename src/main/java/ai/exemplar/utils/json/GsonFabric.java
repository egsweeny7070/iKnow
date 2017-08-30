package ai.exemplar.utils.json;

import ai.exemplar.utils.json.adapters.LocalDateTimeTypeAdapter;
import ai.exemplar.utils.json.adapters.ZonedDateTimeTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class GsonFabric {

    /**
     * Returns gson instance configured to properly serialize and deserialize
     * objects of LocalDateTime and ZonedDateTime classes.
     * @return gson instance.
     */
    public static Gson gson() {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeTypeAdapter())
                .create();
    }
}

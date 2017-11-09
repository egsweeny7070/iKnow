package ai.exemplar.utils.json;

import ai.exemplar.utils.json.adapters.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;

public class GsonFabric {

    /**
     * Returns Gson instance configured to serialize and deserialize
     * objects of LocalDateTime and ZonedDateTime classes
     * using the format specified by ISO-8601.
     * @return Gson instance.
     */
    public static Gson gson() {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeTypeAdapter())
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .registerTypeAdapter(LocalTime.class, new LocalTimeTypeAdapter())
                .create();
    }

    /**
     * Returns Gson instance configured to serialize and deserialize
     * objects of LocalDateTime class
     * using a simple conventional date-time format.
     * @return Gson instance.
     */
    public static Gson simplified() {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new SimplifiedLocalDateTimeTypeAdapter())
                .create();
    }
}

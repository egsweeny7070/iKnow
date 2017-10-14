package ai.exemplar.analytics;

import java.util.Map;

public interface AnalyticsProvider {

    Object get(String location, Map<String, String> query);
}

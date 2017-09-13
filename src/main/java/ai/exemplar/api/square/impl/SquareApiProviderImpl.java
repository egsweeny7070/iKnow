package ai.exemplar.api.square.impl;

import ai.exemplar.api.square.SquareApiProvider;
import ai.exemplar.api.square.model.Merchant;
import ai.exemplar.api.square.model.Payment;
import ai.exemplar.utils.json.GsonFabric;
import com.amazonaws.util.IOUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;

import java.net.SocketTimeoutException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class SquareApiProviderImpl implements SquareApiProvider {

    static final Logger log = Logger.getLogger(SquareApiProviderImpl.class);

    private static final int TIMEOUT = 25 * 1000;

    private final Gson gson = GsonFabric.gson();

    @Override
    public List<Merchant> listLocations(String bearer) {
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet("https://connect.squareup.com/v1/me/locations");

            request.addHeader("Authorization", String.format("Bearer %s", bearer));
            request.addHeader("Accept", "application/json");

            HttpResponse response = client.execute(request);

            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("locations request failed: " +
                        response.getStatusLine().getStatusCode() + " " +
                        response.getStatusLine().getReasonPhrase());
            }

            String responseEntity = IOUtils.toString(response
                    .getEntity().getContent());

            return gson.fromJson(
                    responseEntity,
                    new TypeToken<List<Merchant>>() {}.getType()
            );

        } catch (Throwable e) {
            log.error("listLocations request failed:", e);

            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Payment> listPayments(String bearer, String locationId, LocalDateTime beginTime, LocalDateTime endTime) {
        try {
            HttpGet request = new HttpGet(String.format("https://connect.squareup.com/v1/%s/payments", locationId));

            request.setURI(
                    new URIBuilder(request.getURI())
                            .addParameter("begin_time", beginTime
                                    .toInstant(ZoneOffset.UTC).toString())
                            .addParameter("end_time", endTime
                                    .toInstant(ZoneOffset.UTC).toString())
                            .build()
            );

            List<Payment> payments = new ArrayList<>();

            while (true) {

                request.addHeader("Authorization", String.format("Bearer %s", bearer));
                request.addHeader("Accept", "application/json");

                CloseableHttpClient client = HttpClientBuilder.create()
                        .setDefaultRequestConfig(RequestConfig.custom()
                                .setConnectTimeout(TIMEOUT)
                                .setConnectionRequestTimeout(TIMEOUT)
                                .setSocketTimeout(TIMEOUT)
                                .build())
                        .build();

                try {

                    HttpResponse response = client
                            .execute(request);

                    if (response.getStatusLine().getStatusCode() != 200) {
                        throw new RuntimeException("locations request failed: " +
                                response.getStatusLine().getStatusCode() + " " +
                                response.getStatusLine().getReasonPhrase());
                    }

                    String responseEntity = IOUtils.toString(response
                            .getEntity().getContent());

                    payments.addAll(gson.fromJson(
                            responseEntity,
                            new TypeToken<List<Payment>>() {
                            }.getType()
                    ));

                    Optional<String> next = Stream.of(response
                            .getHeaders("Link")).map(Header::getValue)
                            .map(link -> link.split(";"))
                            .filter(elements -> Stream.of(elements)
                                    .anyMatch("rel='next'"::equals))
                            .map(elements -> elements[0])
                            .map(href -> href.substring(1, href.length() - 1))
                            .findFirst();

                    if (!next.isPresent()) {
                        break;
                    }

                    request = new HttpGet(next.get());

                } catch (ConnectTimeoutException | SocketTimeoutException timeout) {
                    log.info(String.format("listPayments request timeout, url=%s", request.getURI().toString()));

                } finally {
                    client.close();
                }
            }

            return payments;

        } catch (Throwable e) {
            log.error("listPayments request failed:", e);

            throw new RuntimeException(e);
        }
    }
}

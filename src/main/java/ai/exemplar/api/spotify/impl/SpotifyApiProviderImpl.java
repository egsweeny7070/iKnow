package ai.exemplar.api.spotify.impl;

import ai.exemplar.api.spotify.SpotifyApiProvider;
import ai.exemplar.api.spotify.model.*;
import ai.exemplar.utils.json.GsonFabric;
import com.amazonaws.util.IOUtils;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

public class SpotifyApiProviderImpl implements SpotifyApiProvider {

    static final Logger log = Logger.getLogger(SpotifyApiProviderImpl.class);

    private final Gson gson = GsonFabric.gson();

    @Override
    public List<PlayHistoryObject> getRecentlyPlayed(String bearer, LocalDateTime after) {
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet("https://api.spotify.com/v1/me/player/recently-played");

            request.setURI(
                    new URIBuilder(request.getURI())
                            .addParameter("after", Long.toString(after
                                    .toInstant(ZoneOffset.UTC).toEpochMilli()))
                            .build()
            );

            request.addHeader("Authorization", String.format("Bearer %s", bearer));

            HttpResponse response = client.execute(request);

            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("recently-played request failed: " +
                        response.getStatusLine().getStatusCode() + " " +
                        response.getStatusLine().getReasonPhrase());
            }

            String responseEntity = IOUtils.toString(response
                    .getEntity().getContent());

            PagingObject<PlayHistoryObject> recentlyPlayedResponseBody = gson.fromJson(
                    responseEntity,
                    new TypeToken<PagingObject<PlayHistoryObject>>() {}.getType()
            );

            return recentlyPlayedResponseBody.getItems();

        } catch (Throwable e) {
            log.error("getRecentlyPlayed request failed:", e);

            throw new RuntimeException(e);
        }
    }

    @Override
    public List<AudioFeaturesObject> getAudioFeatures(String bearer, List<String> ids) {
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet("https://api.spotify.com/v1/audio-features/");

            request.setURI(
                    new URIBuilder(request.getURI())
                            .addParameter("ids", ids.stream().collect(Collectors
                                    .joining(",")))
                            .build()
            );

            request.addHeader("Authorization", String.format("Bearer %s", bearer));

            HttpResponse response = client.execute(request);

            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("audio-features request failed: " +
                        response.getStatusLine().getStatusCode() + " " +
                        response.getStatusLine().getReasonPhrase());
            }

            String responseEntity = IOUtils.toString(response
                    .getEntity().getContent());

            SeveralAudioFeaturesResponse audioFeaturesResponse = gson.fromJson(
                    responseEntity,
                    SeveralAudioFeaturesResponse.class
            );

            return audioFeaturesResponse.getItems();

        } catch (Throwable e) {
            log.error("getAudioFeatures request failed:", e);

            throw new RuntimeException(e);
        }
    }

    @Override
    public List<TrackObject> getTracks(String bearer, List<String> ids) {
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet("https://api.spotify.com/v1/tracks/");

            request.setURI(
                    new URIBuilder(request.getURI())
                            .addParameter("ids", ids.stream().collect(Collectors
                                    .joining(",")))
                            .build()
            );

            request.addHeader("Authorization", String.format("Bearer %s", bearer));

            HttpResponse response = client.execute(request);

            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("tracks request failed: " +
                        response.getStatusLine().getStatusCode() + " " +
                        response.getStatusLine().getReasonPhrase());
            }

            String responseEntity = IOUtils.toString(response
                    .getEntity().getContent());

            SeveralTracksResponse tracksResponse = gson.fromJson(
                    responseEntity,
                    SeveralTracksResponse.class
            );

            return tracksResponse.getItems();

        } catch (Throwable e) {
            log.error("getTracks request failed:", e);

            throw new RuntimeException(e);
        }
    }

    @Override
    public List<AlbumObject> getAlbums(String bearer, List<String> ids) {
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet("https://api.spotify.com/v1/albums/");

            request.setURI(
                    new URIBuilder(request.getURI())
                            .addParameter("ids", ids.stream().collect(Collectors
                                    .joining(",")))
                            .build()
            );

            request.addHeader("Authorization", String.format("Bearer %s", bearer));

            HttpResponse response = client.execute(request);

            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("albums request failed: " +
                        response.getStatusLine().getStatusCode() + " " +
                        response.getStatusLine().getReasonPhrase());
            }

            String responseEntity = IOUtils.toString(response
                    .getEntity().getContent());

            SeveralAlbumsResponse albumsResponse = gson.fromJson(
                    responseEntity,
                    SeveralAlbumsResponse.class
            );

            return albumsResponse.getItems();

        } catch (Throwable e) {
            log.error("getAlbums request failed:", e);

            throw new RuntimeException(e);
        }
    }

    public static class SeveralAudioFeaturesResponse {

        @SerializedName("audio_features")
        private List<AudioFeaturesObject> items;

        public List<AudioFeaturesObject> getItems() {
            return items;
        }

        public void setItems(List<AudioFeaturesObject> items) {
            this.items = items;
        }
    }

    public static class SeveralTracksResponse {

        @SerializedName("tracks")
        private List<TrackObject> items;

        public List<TrackObject> getItems() {
            return items;
        }

        public void setItems(List<TrackObject> items) {
            this.items = items;
        }
    }

    public static class SeveralAlbumsResponse {

        @SerializedName("albums")
        private List<AlbumObject> items;

        public List<AlbumObject> getItems() {
            return items;
        }

        public void setItems(List<AlbumObject> items) {
            this.items = items;
        }
    }
}

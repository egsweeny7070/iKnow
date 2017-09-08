package ai.exemplar.api.spotify.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class SimplifiedTrackObject {

    private List<LinkObject> artists;

    @SerializedName("available_markets")
    private List<String> availableMarkets;

    @SerializedName("disc_number")
    private Integer discNumber;

    @SerializedName("duration_ms")
    private Integer durationMs;

    private Boolean explicit;

    @SerializedName("external_urls")
    private Map<String, String> externalUrls;

    private String href;

    private String id;

    @SerializedName("is_playable")
    private Boolean isPlayable;

    @SerializedName("linked_from")
    private LinkObject linkedFrom;

    private String name;

    @SerializedName("preview_url")
    private String previewUrl;

    @SerializedName("track_number")
    private Integer trackNumber;

    private String type;

    private String uri;

    public List<LinkObject> getArtists() {
        return artists;
    }

    public void setArtists(List<LinkObject> artists) {
        this.artists = artists;
    }

    public List<String> getAvailableMarkets() {
        return availableMarkets;
    }

    public void setAvailableMarkets(List<String> availableMarkets) {
        this.availableMarkets = availableMarkets;
    }

    public Integer getDiscNumber() {
        return discNumber;
    }

    public void setDiscNumber(Integer discNumber) {
        this.discNumber = discNumber;
    }

    public Integer getDurationMs() {
        return durationMs;
    }

    public void setDurationMs(Integer durationMs) {
        this.durationMs = durationMs;
    }

    public Boolean getExplicit() {
        return explicit;
    }

    public void setExplicit(Boolean explicit) {
        this.explicit = explicit;
    }

    public Map<String, String> getExternalUrls() {
        return externalUrls;
    }

    public void setExternalUrls(Map<String, String> externalUrls) {
        this.externalUrls = externalUrls;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getPlayable() {
        return isPlayable;
    }

    public void setPlayable(Boolean playable) {
        isPlayable = playable;
    }

    public LinkObject getLinkedFrom() {
        return linkedFrom;
    }

    public void setLinkedFrom(LinkObject linkedFrom) {
        this.linkedFrom = linkedFrom;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    public Integer getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(Integer trackNumber) {
        this.trackNumber = trackNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}

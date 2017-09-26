package ai.exemplar.api.spotify.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class AlbumObject {

    @SerializedName("album_type")
    private String albumType;

    private List<LinkObject> artists;

    @SerializedName("available_markets")
    private List<String> markets;

    private List<CopyrightObject> copyrights;

    @SerializedName("external_ids")
    private Map<String, String> externalIds;

    @SerializedName("external_urls")
    private Map<String, String> externalUrls;

    private List<String> genres;

    private String href;

    private String id;

    private List<ImageObject> images;

    private String label;

    private String name;

    private Integer popularity;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("release_date_precision")
    private String releaseDatePrecision;

    private PagingObject<TrackObject> tracks;

    private String type;

    private String uri;

    public String getAlbumType() {
        return albumType;
    }

    public void setAlbumType(String albumType) {
        this.albumType = albumType;
    }

    public List<LinkObject> getArtists() {
        return artists;
    }

    public void setArtists(List<LinkObject> artists) {
        this.artists = artists;
    }

    public List<String> getMarkets() {
        return markets;
    }

    public void setMarkets(List<String> markets) {
        this.markets = markets;
    }

    public List<CopyrightObject> getCopyrights() {
        return copyrights;
    }

    public void setCopyrights(List<CopyrightObject> copyrights) {
        this.copyrights = copyrights;
    }

    public Map<String, String> getExternalIds() {
        return externalIds;
    }

    public void setExternalIds(Map<String, String> externalIds) {
        this.externalIds = externalIds;
    }

    public Map<String, String> getExternalUrls() {
        return externalUrls;
    }

    public void setExternalUrls(Map<String, String> externalUrls) {
        this.externalUrls = externalUrls;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
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

    public List<ImageObject> getImages() {
        return images;
    }

    public void setImages(List<ImageObject> images) {
        this.images = images;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPopularity() {
        return popularity;
    }

    public void setPopularity(Integer popularity) {
        this.popularity = popularity;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getReleaseDatePrecision() {
        return releaseDatePrecision;
    }

    public void setReleaseDatePrecision(String releaseDatePrecision) {
        this.releaseDatePrecision = releaseDatePrecision;
    }

    public PagingObject<TrackObject> getTracks() {
        return tracks;
    }

    public void setTracks(PagingObject<TrackObject> tracks) {
        this.tracks = tracks;
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

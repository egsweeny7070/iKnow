package ai.exemplar.persistence.dynamodb.schema.spotify;

import ai.exemplar.utils.dynamodb.converters.StringListTypeConverter;
import ai.exemplar.utils.dynamodb.converters.StringMapTypeConverter;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;

import java.util.List;
import java.util.Map;

@DynamoDBDocument
public class TrackDocumentSchema {

    private List<LinkDocumentSchema> artists;

    private List<String> markets;

    private Integer discNumber;

    private Integer duration;

    private Boolean explicitLyrics;

    private Map<String, String> externalUrls;

    private String href;

    private String id;

    private Boolean playable;

    private LinkDocumentSchema from;

    private String name;

    private String previewUrl;

    private Integer trackNumber;

    private String type;

    private String uri;

    private AudioFeaturesDocumentSchema features;

    public TrackDocumentSchema() {
    }

    public TrackDocumentSchema(List<LinkDocumentSchema> artists, List<String> markets, Integer discNumber, Integer duration, Boolean explicitLyrics, Map<String, String> externalUrls, String href, String id, Boolean playable, LinkDocumentSchema from, String name, String previewUrl, Integer trackNumber, String type, String uri, AudioFeaturesDocumentSchema features) {
        this.artists = artists;
        this.markets = markets;
        this.discNumber = discNumber;
        this.duration = duration;
        this.explicitLyrics = explicitLyrics;
        this.externalUrls = externalUrls;
        this.href = href;
        this.id = id;
        this.playable = playable;
        this.from = from;
        this.name = name;
        this.previewUrl = previewUrl;
        this.trackNumber = trackNumber;
        this.type = type;
        this.uri = uri;
        this.features = features;
    }

    @DynamoDBAttribute(attributeName = "artists")
    public List<LinkDocumentSchema> getArtists() {
        return artists;
    }

    public void setArtists(List<LinkDocumentSchema> artists) {
        this.artists = artists;
    }

    @DynamoDBTypeConverted(converter = StringListTypeConverter.class)
    @DynamoDBAttribute(attributeName = "markets")
    public List<String> getMarkets() {
        return markets;
    }

    public void setMarkets(List<String> markets) {
        this.markets = markets;
    }

    @DynamoDBAttribute(attributeName = "discNumber")
    public Integer getDiscNumber() {
        return discNumber;
    }

    public void setDiscNumber(Integer discNumber) {
        this.discNumber = discNumber;
    }

    @DynamoDBAttribute(attributeName = "duration")
    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    @DynamoDBAttribute(attributeName = "explicitLyrics")
    public Boolean getExplicitLyrics() {
        return explicitLyrics;
    }

    public void setExplicitLyrics(Boolean explicitLyrics) {
        this.explicitLyrics = explicitLyrics;
    }

    @DynamoDBTypeConverted(converter = StringMapTypeConverter.class)
    @DynamoDBAttribute(attributeName = "externalUrls")
    public Map<String, String> getExternalUrls() {
        return externalUrls;
    }

    public void setExternalUrls(Map<String, String> externalUrls) {
        this.externalUrls = externalUrls;
    }

    @DynamoDBAttribute(attributeName = "href")
    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    @DynamoDBAttribute(attributeName = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @DynamoDBAttribute(attributeName = "playable")
    public Boolean getPlayable() {
        return playable;
    }

    public void setPlayable(Boolean playable) {
        this.playable = playable;
    }

    @DynamoDBAttribute(attributeName = "from")
    public LinkDocumentSchema getFrom() {
        return from;
    }

    public void setFrom(LinkDocumentSchema from) {
        this.from = from;
    }

    @DynamoDBAttribute(attributeName = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @DynamoDBAttribute(attributeName = "previewUrl")
    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    @DynamoDBAttribute(attributeName = "trackNumber")
    public Integer getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(Integer trackNumber) {
        this.trackNumber = trackNumber;
    }

    @DynamoDBAttribute(attributeName = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @DynamoDBAttribute(attributeName = "uri")
    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @DynamoDBAttribute(attributeName = "features")
    public AudioFeaturesDocumentSchema getFeatures() {
        return features;
    }

    public void setFeatures(AudioFeaturesDocumentSchema features) {
        this.features = features;
    }
}

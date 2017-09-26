package ai.exemplar.persistence.dynamodb.schema.spotify;

import ai.exemplar.utils.dynamodb.converters.StringListTypeConverter;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;

import java.util.List;

@DynamoDBDocument
public class AlbumDocumentSchema {

    private String type;

    private List<LinkDocumentSchema> artists;

    private List<String> markets;

    private List<String> genres;

    private String href;

    private String id;

    private List<ImageDocumentSchema> images;

    private String label;

    private String name;

    private Integer popularity;

    private String releaseDate;

    private String releaseDatePrecision;

    private String uri;

    public AlbumDocumentSchema() {
    }

    public AlbumDocumentSchema(String type, List<LinkDocumentSchema> artists, List<String> markets, List<String> genres, String href, String id, List<ImageDocumentSchema> images, String label, String name, Integer popularity, String releaseDate, String releaseDatePrecision, String uri) {
        this.type = type;
        this.artists = artists;
        this.markets = markets;
        this.genres = genres;
        this.href = href;
        this.id = id;
        this.images = images;
        this.label = label;
        this.name = name;
        this.popularity = popularity;
        this.releaseDate = releaseDate;
        this.releaseDatePrecision = releaseDatePrecision;
        this.uri = uri;
    }

    @DynamoDBAttribute(attributeName = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    @DynamoDBTypeConverted(converter = StringListTypeConverter.class)
    @DynamoDBAttribute(attributeName = "genres")
    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
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

    @DynamoDBAttribute(attributeName = "images")
    public List<ImageDocumentSchema> getImages() {
        return images;
    }

    public void setImages(List<ImageDocumentSchema> images) {
        this.images = images;
    }

    @DynamoDBAttribute(attributeName = "label")
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @DynamoDBAttribute(attributeName = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @DynamoDBAttribute(attributeName = "popularity")
    public Integer getPopularity() {
        return popularity;
    }

    public void setPopularity(Integer popularity) {
        this.popularity = popularity;
    }

    @DynamoDBAttribute(attributeName = "releaseDate")
    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @DynamoDBAttribute(attributeName = "releaseDatePrecision")
    public String getReleaseDatePrecision() {
        return releaseDatePrecision;
    }

    public void setReleaseDatePrecision(String releaseDatePrecision) {
        this.releaseDatePrecision = releaseDatePrecision;
    }

    @DynamoDBAttribute(attributeName = "uri")
    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}

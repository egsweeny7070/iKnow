package ai.exemplar.upload.service.values;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;

public class UploadStreamRecord {

    @SerializedName("INGEST_TIME")
    private LocalDateTime ingest;

    @SerializedName("LOCATION")
    private String location;

    @SerializedName("TOTAL_PAYMENTS_COUNT")
    private Integer totalPaymentsCount;

    @SerializedName("TOTAL_ITEMS_COUNT")
    private Integer totalItemsCount;

    @SerializedName("TOTAL_DISCOUNT_PERCENT")
    private Double totalDiscountPercent;

    @SerializedName("COLLECTED_AMOUNT")
    private Double collectedAmount;

    @SerializedName("TRACKS_COUNT")
    private Integer tracksCount;

    @SerializedName("SUM_ACOUSTICNESS")
    private Double sumAcousticness;

    @SerializedName("SUM_DANCEABILITY")
    private Double sumDanceability;

    @SerializedName("SUM_ENERGY")
    private Double sumEnergy;

    @SerializedName("SUM_INSTRUMENTALNESS")
    private Double sumInstrumentalness;

    @SerializedName("SUM_LIVENESS")
    private Double sumLiveness;

    @SerializedName("SUM_LOUDNESS")
    private Double sumLoudness;

    @SerializedName("SUM_SPEECHINESS")
    private Double sumSpeechiness;

    @SerializedName("SUM_VALENCE")
    private Double sumValence;

    @SerializedName("SUM_DURATION")
    private Double sumDuration;

    @SerializedName("SUM_TEMPO")
    private Double sumTempo;

    @SerializedName("MIN_ACOUSTICNESS")
    private Double minAcousticness;

    @SerializedName("MIN_DANCEABILITY")
    private Double minDanceability;

    @SerializedName("MIN_ENERGY")
    private Double minEnergy;

    @SerializedName("MIN_INSTRUMENTALNESS")
    private Double minInstrumentalness;

    @SerializedName("MIN_LIVENESS")
    private Double minLiveness;

    @SerializedName("MIN_LOUDNESS")
    private Double minLoudness;

    @SerializedName("MIN_SPEECHINESS")
    private Double minSpeechiness;

    @SerializedName("MIN_VALENCE")
    private Double minValence;

    @SerializedName("MIN_DURATION")
    private Double minDuration;

    @SerializedName("MIN_TEMPO")
    private Double minTempo;

    @SerializedName("MAX_ACOUSTICNESS")
    private Double maxAcousticness;

    @SerializedName("MAX_DANCEABILITY")
    private Double maxDanceability;

    @SerializedName("MAX_ENERGY")
    private Double maxEnergy;

    @SerializedName("MAX_INSTRUMENTALNESS")
    private Double maxInstrumentalness;

    @SerializedName("MAX_LIVENESS")
    private Double maxLiveness;

    @SerializedName("MAX_LOUDNESS")
    private Double maxLoudness;

    @SerializedName("MAX_SPEECHINESS")
    private Double maxSpeechiness;

    @SerializedName("MAX_VALENCE")
    private Double maxValence;

    @SerializedName("MAX_DURATION")
    private Double maxDuration;

    @SerializedName("MAX_TEMPO")
    private Double maxTempo;

    @SerializedName("STD_ACOUSTICNESS")
    private Double stdAcousticness;

    @SerializedName("STD_DANCEABILITY")
    private Double stdDanceability;

    @SerializedName("STD_ENERGY")
    private Double stdEnergy;

    @SerializedName("STD_INSTRUMENTALNESS")
    private Double stdInstrumentalness;

    @SerializedName("STD_LIVENESS")
    private Double stdLiveness;

    @SerializedName("STD_LOUDNESS")
    private Double stdLoudness;

    @SerializedName("STD_SPEECHINESS")
    private Double stdSpeechiness;

    @SerializedName("STD_VALENCE")
    private Double stdValence;

    @SerializedName("STD_DURATION")
    private Double stdDuration;

    @SerializedName("STD_TEMPO")
    private Double stdTempo;

    public LocalDateTime getIngest() {
        return ingest;
    }

    public void setIngest(LocalDateTime ingest) {
        this.ingest = ingest;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getTotalPaymentsCount() {
        return totalPaymentsCount;
    }

    public void setTotalPaymentsCount(Integer totalPaymentsCount) {
        this.totalPaymentsCount = totalPaymentsCount;
    }

    public Integer getTotalItemsCount() {
        return totalItemsCount;
    }

    public void setTotalItemsCount(Integer totalItemsCount) {
        this.totalItemsCount = totalItemsCount;
    }

    public Double getTotalDiscountPercent() {
        return totalDiscountPercent;
    }

    public void setTotalDiscountPercent(Double totalDiscountPercent) {
        this.totalDiscountPercent = totalDiscountPercent;
    }

    public Double getCollectedAmount() {
        return collectedAmount;
    }

    public void setCollectedAmount(Double collectedAmount) {
        this.collectedAmount = collectedAmount;
    }

    public Integer getTracksCount() {
        return tracksCount;
    }

    public void setTracksCount(Integer tracksCount) {
        this.tracksCount = tracksCount;
    }

    public Double getSumAcousticness() {
        return sumAcousticness;
    }

    public void setSumAcousticness(Double sumAcousticness) {
        this.sumAcousticness = sumAcousticness;
    }

    public Double getSumDanceability() {
        return sumDanceability;
    }

    public void setSumDanceability(Double sumDanceability) {
        this.sumDanceability = sumDanceability;
    }

    public Double getSumEnergy() {
        return sumEnergy;
    }

    public void setSumEnergy(Double sumEnergy) {
        this.sumEnergy = sumEnergy;
    }

    public Double getSumInstrumentalness() {
        return sumInstrumentalness;
    }

    public void setSumInstrumentalness(Double sumInstrumentalness) {
        this.sumInstrumentalness = sumInstrumentalness;
    }

    public Double getSumLiveness() {
        return sumLiveness;
    }

    public void setSumLiveness(Double sumLiveness) {
        this.sumLiveness = sumLiveness;
    }

    public Double getSumLoudness() {
        return sumLoudness;
    }

    public void setSumLoudness(Double sumLoudness) {
        this.sumLoudness = sumLoudness;
    }

    public Double getSumSpeechiness() {
        return sumSpeechiness;
    }

    public void setSumSpeechiness(Double sumSpeechiness) {
        this.sumSpeechiness = sumSpeechiness;
    }

    public Double getSumValence() {
        return sumValence;
    }

    public void setSumValence(Double sumValence) {
        this.sumValence = sumValence;
    }

    public Double getSumDuration() {
        return sumDuration;
    }

    public void setSumDuration(Double sumDuration) {
        this.sumDuration = sumDuration;
    }

    public Double getSumTempo() {
        return sumTempo;
    }

    public void setSumTempo(Double sumTempo) {
        this.sumTempo = sumTempo;
    }

    public Double getMinAcousticness() {
        return minAcousticness;
    }

    public void setMinAcousticness(Double minAcousticness) {
        this.minAcousticness = minAcousticness;
    }

    public Double getMinDanceability() {
        return minDanceability;
    }

    public void setMinDanceability(Double minDanceability) {
        this.minDanceability = minDanceability;
    }

    public Double getMinEnergy() {
        return minEnergy;
    }

    public void setMinEnergy(Double minEnergy) {
        this.minEnergy = minEnergy;
    }

    public Double getMinInstrumentalness() {
        return minInstrumentalness;
    }

    public void setMinInstrumentalness(Double minInstrumentalness) {
        this.minInstrumentalness = minInstrumentalness;
    }

    public Double getMinLiveness() {
        return minLiveness;
    }

    public void setMinLiveness(Double minLiveness) {
        this.minLiveness = minLiveness;
    }

    public Double getMinLoudness() {
        return minLoudness;
    }

    public void setMinLoudness(Double minLoudness) {
        this.minLoudness = minLoudness;
    }

    public Double getMinSpeechiness() {
        return minSpeechiness;
    }

    public void setMinSpeechiness(Double minSpeechiness) {
        this.minSpeechiness = minSpeechiness;
    }

    public Double getMinValence() {
        return minValence;
    }

    public void setMinValence(Double minValence) {
        this.minValence = minValence;
    }

    public Double getMinDuration() {
        return minDuration;
    }

    public void setMinDuration(Double minDuration) {
        this.minDuration = minDuration;
    }

    public Double getMinTempo() {
        return minTempo;
    }

    public void setMinTempo(Double minTempo) {
        this.minTempo = minTempo;
    }

    public Double getMaxAcousticness() {
        return maxAcousticness;
    }

    public void setMaxAcousticness(Double maxAcousticness) {
        this.maxAcousticness = maxAcousticness;
    }

    public Double getMaxDanceability() {
        return maxDanceability;
    }

    public void setMaxDanceability(Double maxDanceability) {
        this.maxDanceability = maxDanceability;
    }

    public Double getMaxEnergy() {
        return maxEnergy;
    }

    public void setMaxEnergy(Double maxEnergy) {
        this.maxEnergy = maxEnergy;
    }

    public Double getMaxInstrumentalness() {
        return maxInstrumentalness;
    }

    public void setMaxInstrumentalness(Double maxInstrumentalness) {
        this.maxInstrumentalness = maxInstrumentalness;
    }

    public Double getMaxLiveness() {
        return maxLiveness;
    }

    public void setMaxLiveness(Double maxLiveness) {
        this.maxLiveness = maxLiveness;
    }

    public Double getMaxLoudness() {
        return maxLoudness;
    }

    public void setMaxLoudness(Double maxLoudness) {
        this.maxLoudness = maxLoudness;
    }

    public Double getMaxSpeechiness() {
        return maxSpeechiness;
    }

    public void setMaxSpeechiness(Double maxSpeechiness) {
        this.maxSpeechiness = maxSpeechiness;
    }

    public Double getMaxValence() {
        return maxValence;
    }

    public void setMaxValence(Double maxValence) {
        this.maxValence = maxValence;
    }

    public Double getMaxDuration() {
        return maxDuration;
    }

    public void setMaxDuration(Double maxDuration) {
        this.maxDuration = maxDuration;
    }

    public Double getMaxTempo() {
        return maxTempo;
    }

    public void setMaxTempo(Double maxTempo) {
        this.maxTempo = maxTempo;
    }

    public Double getStdAcousticness() {
        return stdAcousticness;
    }

    public void setStdAcousticness(Double stdAcousticness) {
        this.stdAcousticness = stdAcousticness;
    }

    public Double getStdDanceability() {
        return stdDanceability;
    }

    public void setStdDanceability(Double stdDanceability) {
        this.stdDanceability = stdDanceability;
    }

    public Double getStdEnergy() {
        return stdEnergy;
    }

    public void setStdEnergy(Double stdEnergy) {
        this.stdEnergy = stdEnergy;
    }

    public Double getStdInstrumentalness() {
        return stdInstrumentalness;
    }

    public void setStdInstrumentalness(Double stdInstrumentalness) {
        this.stdInstrumentalness = stdInstrumentalness;
    }

    public Double getStdLiveness() {
        return stdLiveness;
    }

    public void setStdLiveness(Double stdLiveness) {
        this.stdLiveness = stdLiveness;
    }

    public Double getStdLoudness() {
        return stdLoudness;
    }

    public void setStdLoudness(Double stdLoudness) {
        this.stdLoudness = stdLoudness;
    }

    public Double getStdSpeechiness() {
        return stdSpeechiness;
    }

    public void setStdSpeechiness(Double stdSpeechiness) {
        this.stdSpeechiness = stdSpeechiness;
    }

    public Double getStdValence() {
        return stdValence;
    }

    public void setStdValence(Double stdValence) {
        this.stdValence = stdValence;
    }

    public Double getStdDuration() {
        return stdDuration;
    }

    public void setStdDuration(Double stdDuration) {
        this.stdDuration = stdDuration;
    }

    public Double getStdTempo() {
        return stdTempo;
    }

    public void setStdTempo(Double stdTempo) {
        this.stdTempo = stdTempo;
    }
}

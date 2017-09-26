package ai.exemplar.api.spotify.model;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;

public class PlayHistoryObject {

    private TrackObject track;

    @SerializedName("played_at")
    private LocalDateTime playedAt;

    private LinkObject context;

    public TrackObject getTrack() {
        return track;
    }

    public void setTrack(TrackObject track) {
        this.track = track;
    }

    public LocalDateTime getPlayedAt() {
        return playedAt;
    }

    public void setPlayedAt(LocalDateTime playedAt) {
        this.playedAt = playedAt;
    }

    public LinkObject getContext() {
        return context;
    }

    public void setContext(LinkObject context) {
        this.context = context;
    }
}

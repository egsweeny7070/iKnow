package ai.exemplar.analytics.providers.tracks.playlists.values;

import ai.exemplar.persistence.dynamodb.schema.spotify.LinkDocumentSchema;

import java.time.LocalTime;

public class PlaylistPeriodItem {

    private LocalTime from;

    private LocalTime to;

    private LinkDocumentSchema context;

    public PlaylistPeriodItem(LocalTime from, LocalTime to, LinkDocumentSchema context) {
        this.from = from;
        this.to = to;
        this.context = context;
    }

    public LocalTime getFrom() {
        return from;
    }

    public void setFrom(LocalTime from) {
        this.from = from;
    }

    public LocalTime getTo() {
        return to;
    }

    public void setTo(LocalTime to) {
        this.to = to;
    }

    public LinkDocumentSchema getContext() {
        return context;
    }

    public void setContext(LinkDocumentSchema context) {
        this.context = context;
    }
}

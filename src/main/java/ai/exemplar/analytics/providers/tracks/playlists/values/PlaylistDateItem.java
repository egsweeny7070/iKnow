package ai.exemplar.analytics.providers.tracks.playlists.values;

import java.time.LocalDate;
import java.util.List;

public class PlaylistDateItem {

    private LocalDate date;

    private List<PlaylistPeriodItem> periods;

    public PlaylistDateItem(LocalDate date, List<PlaylistPeriodItem> periods) {
        this.date = date;
        this.periods = periods;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<PlaylistPeriodItem> getPeriods() {
        return periods;
    }

    public void setPeriods(List<PlaylistPeriodItem> periods) {
        this.periods = periods;
    }
}

package ai.exemplar.api.spotify.model;

import java.util.List;

public class RecentlyPlayedResponse {

    private List<PlayHistoryObject> items;

    public List<PlayHistoryObject> getItems() {
        return items;
    }

    public void setItems(List<PlayHistoryObject> items) {
        this.items = items;
    }
}

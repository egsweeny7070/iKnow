package ai.exemplar.api.spotify.model;

import java.util.List;

public class PagingObject<T> {

    private List<T> items;

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}

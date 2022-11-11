package co.empathy.academy.demo_search.ports.requests.senders.util;

import java.util.ArrayList;
import java.util.List;

public class SearchResponse {
    private List<Object> hits;

    public <T> SearchResponse setHits(List<T> hits) {
        this.hits = new ArrayList<>(hits);
        return this;
    }
}

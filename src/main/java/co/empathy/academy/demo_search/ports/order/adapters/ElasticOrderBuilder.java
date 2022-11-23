package co.empathy.academy.demo_search.ports.order.adapters;

import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.empathy.academy.demo_search.ports.order.POrderBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ElasticOrderBuilder implements POrderBuilder {

    private Map<String, SortOrder> sortings;

    public ElasticOrderBuilder() {
        this.sortings = new HashMap<>();
    }

    @Override
    public POrderBuilder byField(String field, Order order) {
        sortings.put(field, mapOrder(order));
        return this;
    }

    private SortOrder mapOrder(Order order) {
        switch (order) {
            case ASC -> { return SortOrder.Asc; }
            case DESC -> { return SortOrder.Desc; }
            default -> { return null; }
        }
    }

    @Override
    public List<SortOptions> build() {
        return sortings.keySet().stream()
                .map(k ->
                        (new SortOptions.Builder())
                                .field(f -> f.field(k).order(sortings.get(k)))
                                .build()
                ).collect(Collectors.toList());
    }
}

package co.empathy.academy.demo_search.ports.order;

import co.elastic.clients.elasticsearch._types.SortOptions;

import java.util.List;

public interface POrderBuilder {
    public enum Order {
        ASC, DESC
    }

    POrderBuilder byField(String field, Order order);

    /**
     * Builds the options
     * @return the sort options
     */
    List<SortOptions> build();
}

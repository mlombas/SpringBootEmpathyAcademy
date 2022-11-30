package co.empathy.academy.demo_search.ports.order;

import co.elastic.clients.elasticsearch._types.SortOptions;

import java.util.List;

public interface POrderBuilder {
    public enum Order {
        ASC, DESC
    }

    /**
     * Builds the order by field
     * @param field the field to sort by
     * @param order ascendant or descendant
     * @see POrderBuilder.Order
     *
     * @return POrderBuilder for fluid interface
     */
    POrderBuilder byField(String field, Order order);

    /**
     * Builds the options
     * @return the sort options
     */
    List<SortOptions> build();
}

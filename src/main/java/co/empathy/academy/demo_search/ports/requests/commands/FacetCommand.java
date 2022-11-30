package co.empathy.academy.demo_search.ports.requests.commands;

import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;

public interface FacetCommand {
    /**
     * @return the facet
     */
    Aggregation getFacet();

    /**
     * @return the name
     */
    String getName();
}

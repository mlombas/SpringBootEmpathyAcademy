package co.empathy.academy.demo_search.ports.requests.commands;

import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;

public interface FacetCommand {
    Aggregation getFacet();
    String getName();
}

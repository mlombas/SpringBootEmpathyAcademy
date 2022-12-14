package co.empathy.academy.demo_search.ports.requests.commands.facets;

import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.empathy.academy.demo_search.ports.requests.commands.FacetCommand;

public class ElasticTermsAggregation implements FacetCommand {
    private Aggregation facet;
    private String name;

    public ElasticTermsAggregation(String name, String field) {
        this.name = name;
        facet = new Aggregation.Builder()
                .terms(t -> t.field(field).size(100))
                .build();
    }

    @Override
    public Aggregation getFacet() {
        return this.facet;
    }

    @Override
    public String getName() {
        return name;
    }
}

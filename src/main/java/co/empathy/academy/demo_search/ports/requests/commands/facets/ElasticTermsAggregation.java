package co.empathy.academy.demo_search.ports.requests.commands.aggregations;

import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.empathy.academy.demo_search.ports.requests.commands.FacetCommand;

public class ElasticTermsAggregation implements FacetCommand {
    private Aggregation facet;

    public ElasticTermsAggregation(String field) {
        facet = new Aggregation.Builder()
                .terms(t -> t.field(field))
                .build();
    }

    @Override
    public Aggregation getFacet() {
        return this.facet;
    }

    @Override
    public String getName() {
        return "facet";
    }
}

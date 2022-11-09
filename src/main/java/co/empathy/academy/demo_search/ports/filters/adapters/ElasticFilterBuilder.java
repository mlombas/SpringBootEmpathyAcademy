package co.empathy.academy.demo_search.ports.filters.adapters;

import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.empathy.academy.demo_search.ports.filters.PFilterBuilder;

import java.util.Map;

public class ElasticFilterBuilder implements PFilterBuilder {
    private BoolQuery.Builder builder;

    public ElasticFilterBuilder() {
        builder = new BoolQuery.Builder();
    }

    @Override
    public PFilterBuilder range(String field, String from, String to) {
        builder.must(b -> b.range(r ->
                r.field(field)
                        .from(from)
                        .to(to)
                ));
        return this;
    }

    @Override
    public Query build() {
        return builder.build()._toQuery();
    }
}

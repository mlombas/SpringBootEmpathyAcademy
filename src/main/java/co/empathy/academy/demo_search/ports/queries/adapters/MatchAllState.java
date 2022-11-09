package co.empathy.academy.demo_search.ports.queries.adapters;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchAllQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;

public class MatchAllState extends DefaultState {
    private final MatchAllQuery.Builder builder;

    public MatchAllState() {
        this.builder = QueryBuilders.matchAll();
    }

    @Override
    public Query build() {
        return builder.build()._toQuery();
    }
}

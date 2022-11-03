package co.empathy.academy.demo_search.ports.queries.adapters;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;

public interface ElasticQueryBuilderState {
    ElasticQueryBuilderState must();
    ElasticQueryBuilderState match(String field, String value);

    Query build();
}

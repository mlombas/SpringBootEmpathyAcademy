package co.empathy.academy.demo_search.ports.queries.adapters;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;

import java.util.List;

public interface ElasticQueryBuilderState {
    ElasticQueryBuilderState must();
    ElasticQueryBuilderState should();
    ElasticQueryBuilderState match(String field, String value);

    Query build();

    ElasticQueryBuilderState multi(String query, List<String> fields);

    ElasticQueryBuilderState multi(String query, String... fields);
}

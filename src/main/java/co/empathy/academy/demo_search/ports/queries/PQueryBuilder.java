package co.empathy.academy.demo_search.ports.queries;


import co.elastic.clients.elasticsearch._types.query_dsl.Query;

import java.util.List;

public interface PQueryBuilder {
    PQueryBuilder must();
    PQueryBuilder should();

    PQueryBuilder match(String field, String value);

    PQueryBuilder multi(String query, List<String> fields);
    PQueryBuilder multi(String query, String... fields);

    Query build();
}

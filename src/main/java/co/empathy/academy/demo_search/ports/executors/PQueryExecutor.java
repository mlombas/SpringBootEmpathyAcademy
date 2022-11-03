package co.empathy.academy.demo_search.ports.executors;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;

import java.util.List;

public interface PQueryExecutor {
    public <T> List<T> executeQuery(Query q, Class<T> clazz);
}

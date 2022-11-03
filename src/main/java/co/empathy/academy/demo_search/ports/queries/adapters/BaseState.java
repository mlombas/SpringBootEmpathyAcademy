package co.empathy.academy.demo_search.ports.queries.adapters;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import org.elasticsearch.index.query.QueryBuilder;

public class BaseState implements ElasticQueryBuilderState {

    @Override
    public ElasticQueryBuilderState must() {
        return new MustState();
    }

    @Override
    public ElasticQueryBuilderState match(String field, String value) {
        throw new ElasticStateException("Base", "match");
    }

    @Override
    public Query build() {
        throw new ElasticStateException("Base", "build");
    }
}

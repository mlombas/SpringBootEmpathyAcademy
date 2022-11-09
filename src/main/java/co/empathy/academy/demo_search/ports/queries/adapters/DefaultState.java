package co.empathy.academy.demo_search.ports.queries.adapters;

import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;

import java.util.Arrays;
import java.util.List;

public abstract class DefaultState implements ElasticQueryBuilderState {
    @Override
    public ElasticQueryBuilderState must() {
        throw new ElasticStateException(this.getClass().getName(), "must");
    }
    @Override
    public ElasticQueryBuilderState should() {
        throw new ElasticStateException(this.getClass().getName(), "should");
    }

    @Override
    public ElasticQueryBuilderState match(String field, String value) {
        throw new ElasticStateException(this.getClass().getName(), "match");
    }

    @Override
    public ElasticQueryBuilderState multi(String query, List<String> fields) {
        throw new ElasticStateException(this.getClass().getName(), "multi");
    }
    @Override
    public final ElasticQueryBuilderState multi(String query, String... fields) {
        return multi(query, Arrays.stream(fields).toList());
    }

    @Override
    public ElasticQueryBuilderState all() {
        throw new ElasticStateException(this.getClass().getName(), "all");
    }
}

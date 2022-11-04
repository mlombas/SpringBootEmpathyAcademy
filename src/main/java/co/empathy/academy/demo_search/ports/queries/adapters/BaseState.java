package co.empathy.academy.demo_search.ports.queries.adapters;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;

import java.util.List;

public class BaseState extends DefaultState {

    @Override
    public ElasticQueryBuilderState must() {
        return new MustState();
    }

    @Override
    public ElasticQueryBuilderState should() {
        return new ShouldState();
    }

    @Override
    public ElasticQueryBuilderState multi(String query, List<String> fields) {
        return new MultiMatchState(query, fields);
    }

    @Override
    public Query build() {
        throw new ElasticStateException("Base", "build");
    }
}

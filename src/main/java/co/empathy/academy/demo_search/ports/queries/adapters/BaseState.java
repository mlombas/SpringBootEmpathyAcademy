package co.empathy.academy.demo_search.ports.queries.adapters;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
public class BaseState implements ElasticQueryBuilderState {

    @Override
    public ElasticQueryBuilderState must() {
        System.out.println("Called must from base");
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

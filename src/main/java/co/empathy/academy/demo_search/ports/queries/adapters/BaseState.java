package co.empathy.academy.demo_search.ports.queries.adapters;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
public class BaseState extends DefaultState {

    @Override
    public ElasticQueryBuilderState must() {
        return new MustState();
    }

    @Override
    public Query build() {
        throw new ElasticStateException("Base", "build");
    }
}

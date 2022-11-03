package co.empathy.academy.demo_search.ports.queries.adapters;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.empathy.academy.demo_search.ports.queries.PQueryBuilder;

import java.util.Arrays;
import java.util.List;

public class ElasticQueryBuilder implements PQueryBuilder {

    private ElasticQueryBuilderState state;

    public ElasticQueryBuilder() {
        this.state = new BaseState();
    }

    @Override
    public PQueryBuilder must() {
        this.state = this.state.must();
        return this;
    }

    @Override
    public PQueryBuilder should() {
        this.state = this.state.should();
        return this;
    }

    @Override
    public PQueryBuilder match(String field, String value) {
        this.state = this.state.match(field, value);
        return this;
    }

    @Override
    public PQueryBuilder multi(String query, List<String> fields) {
        this.state = this.state.multi(query, fields);
        return this;
    }
    @Override
    public PQueryBuilder multi(String query, String... fields) {
        return multi(query, Arrays.stream(fields).toList());
    }

    @Override
    public Query build() {
        Query q = state.build();

        //Renew state, to make a fresh builder
        state = new BaseState();

        return q;
    }
}

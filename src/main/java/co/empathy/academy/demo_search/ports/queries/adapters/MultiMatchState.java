package co.empathy.academy.demo_search.ports.queries.adapters;

import co.elastic.clients.elasticsearch._types.query_dsl.MultiMatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import org.elasticsearch.client.ml.inference.preprocessing.Multi;

import java.util.List;

public class MultiMatchState extends DefaultState {
    private final MultiMatchQuery.Builder builder;

    public MultiMatchState(String value, List<String> fields) {
        this.builder = QueryBuilders.multiMatch()
                .query(value)
                .fields(fields);
    }

    @Override
    public Query build() {
        return this.builder.build()._toQuery();
    }
}

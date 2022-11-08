package co.empathy.academy.demo_search.ports.requests.commands.search;

import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.empathy.academy.demo_search.ports.aggregations.PAggregationBuilder;
import co.empathy.academy.demo_search.ports.queries.PQueryBuilder;
import co.empathy.academy.demo_search.ports.requests.commands.SearchCommand;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public abstract class DefaultSearchCommand<T> implements SearchCommand<T> {
    @Override
    public Query buildQuery(PQueryBuilder builder) {
        return builder.all().build();
    }

    @Override
    public Map<String, Aggregation> buildAggregagtion(PAggregationBuilder builer) {
        return new HashMap<>();
    }
}

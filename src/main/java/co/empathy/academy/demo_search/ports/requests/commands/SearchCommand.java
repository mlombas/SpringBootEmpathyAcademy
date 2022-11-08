package co.empathy.academy.demo_search.ports.requests.commands;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.empathy.academy.demo_search.ports.aggregations.PAggregationBuilder;
import co.empathy.academy.demo_search.ports.queries.PQueryBuilder;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public interface SearchCommand<T> extends Consumer<List<T>> {
    Query buildQuery(PQueryBuilder builder);
    Map<String, Aggregation> buildAggregagtion(PAggregationBuilder builer);

    CompletableFuture<List<T>> getFuture();
    void accept(List<T> returns);
    Class<T> getInnerClass();
}

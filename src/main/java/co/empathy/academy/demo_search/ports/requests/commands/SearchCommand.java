package co.empathy.academy.demo_search.ports.requests.commands;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.empathy.academy.demo_search.ports.filters.PFilterBuilder;
import co.empathy.academy.demo_search.ports.queries.PQueryBuilder;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * Represents the command to search for anything
 * @param <T> the thing to search
 */
public interface SearchCommand<T> {
    /**
     * Builds the query the command wants
     * @param builder the query builder
     * @return the query built
     */
    Query buildQuery(PQueryBuilder builder);

    /**
     * Builds the filter the command wants
     * @param builder the filter builder
     * @return the filter built
     */
    Query buildFilter(PFilterBuilder builder);

    /**
     * Future which will be completed when the command finishes
     * @return the future
     */
    CompletableFuture<List<T>> getFuture();

    /**
     * Makes the command accept a result
     * @param what the result of the command
     */
    void accept(List<T> what);
    Class<T> getInnerClass();
}

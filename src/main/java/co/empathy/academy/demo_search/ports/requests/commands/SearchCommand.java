package co.empathy.academy.demo_search.ports.requests.commands;

import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.empathy.academy.demo_search.ports.filters.PFilterBuilder;
import co.empathy.academy.demo_search.ports.order.POrderBuilder;
import co.empathy.academy.demo_search.ports.queries.PQueryBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;

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
     * Builds the sorting order the command wants
     *
     * @param builder the order builder
     * @return the order built
     */
    List<SortOptions> buildOrder(POrderBuilder builder);

    /**
     * Future which will be completed when the command finishes
     * @return the future
     */
    CompletableFuture<List<T>> getFuture();


    /**
     * Maximum number of hits to return
     * @return the max number of hits
     */
    Integer getMaxNHits();

    /**
     * Makes the command accept a result
     * @param what the result of the command
     */
    void accept(List<T> what);
    Class<T> getInnerClass();
}

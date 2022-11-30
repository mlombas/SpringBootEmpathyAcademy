package co.empathy.academy.demo_search.ports.executors;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.empathy.academy.demo_search.ports.requests.commands.SearchFacetsCommand;

import java.util.List;

/**
 * Port that represents an adapter capable of executing queries
 */
public interface PQueryExecutor {
    /**
     * Executes the query passed in
     * @param sr The search request
     * @param clazz the return class
     * @return a list of results the query has returned
     * @param <T> the type of the result
     */
    <T> List<T> executeQuery(SearchRequest sr, Class<T> clazz);

    /**
     * Executes a query passed in, with its bundled facets
     *
     * @param sr The search request, with facets
     * @param clazz the return class
     * @return a list of results, hits and facets
     * @param <T> the type of the result
     */
    <T> SearchFacetsCommand.Result<T> executeSearchFacetQuery(SearchRequest sr, Class<T> clazz);
}

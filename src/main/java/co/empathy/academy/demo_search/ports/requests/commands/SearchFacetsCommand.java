package co.empathy.academy.demo_search.ports.requests.commands;

import co.elastic.clients.elasticsearch._types.aggregations.Aggregate;
import lombok.Value;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Represents the command to search for anything, while returning facets
 * @param <T> the thing to search
 */
public interface SearchFacetsCommand<T> {
    /**
     * Result of a search facet command, which includes query results as well as
     * facets
     * @param hits the hits
     * @param facets the facets
     * @param <T> The type of the hits
     */
    @Value
    record Result<T>(List<T> hits, Map<String, Aggregate> facets) {}

    /**
     * Gets the search command
     * @return
     */
    SearchCommand<T> getSearch();

    /**
     * Gets the facets bundled with the search command
     * @return the facet commands
     */
    List<FacetCommand> getFacets();


    void accept(Result result);
    CompletableFuture<Result> getFuture();
}

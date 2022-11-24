package co.empathy.academy.demo_search.ports.requests.commands;

import co.elastic.clients.elasticsearch._types.aggregations.Aggregate;
import lombok.Value;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface SearchFacetsCommand<T> {
    @Value
    public static class Result<T> {
        List<T> hits;
        Map<String, Aggregate> aggregates;
    }

    SearchCommand<T> getSearch();
    List<FacetCommand> getFacets();
    void accept(Result result);
    CompletableFuture<Result> getFuture();
}

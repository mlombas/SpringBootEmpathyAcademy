package co.empathy.academy.demo_search.ports.requests.commands;

<<<<<<< HEAD
import co.elastic.clients.elasticsearch._types.aggregations.Aggregate;
import co.empathy.academy.demo_search.model.Title;
import lombok.Value;
import org.apache.lucene.search.suggest.document.CompletionAnalyzer;

import java.util.Comparator;
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
=======
public interface SearchWithAggregationsCommand<T> {
    SearchCommand<T> search;

>>>>>>> main
}

package co.empathy.academy.demo_search.ports.requests.commands.searchfacets;

import co.empathy.academy.demo_search.ports.requests.commands.FacetCommand;
import co.empathy.academy.demo_search.ports.requests.commands.SearchCommand;
import co.empathy.academy.demo_search.ports.requests.commands.SearchFacetsCommand;
import io.micrometer.core.instrument.search.Search;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class BasicSearchFacetsCommand<T> implements SearchFacetsCommand<T> {
    private CompletableFuture<Result> future;
    private SearchCommand<T> search;
    private List<FacetCommand> facets;

    public BasicSearchFacetsCommand(SearchCommand<T> search, FacetCommand... facets) {
        this.search = search;
        this.facets = Arrays.stream(facets).toList();

        this.future = new CompletableFuture<>();
    }

    @Override
    public SearchCommand<T> getSearch() {
        return search;
    }

    @Override
    public List<FacetCommand> getFacets() {
        return facets;
    }

    @Override
    public void accept(Result result) {
        this.future.complete(result);
    }

    @Override
    public CompletableFuture<Result> getFuture() {
        return future;
    }
}

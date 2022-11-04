package co.empathy.academy.demo_search.ports.requests.commands;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.empathy.academy.demo_search.model.Movie;
import co.empathy.academy.demo_search.ports.queries.PQueryBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class InTitleSearchCommand implements SearchCommand<Movie> {
    private final String intitle;
    private CompletableFuture<List<Movie>> future;

    public InTitleSearchCommand(String intitle) {
        this.intitle = intitle;
        this.future = new CompletableFuture<>();
    }

    @Override
    public Query build(PQueryBuilder builder) {
        return builder.multi(
                intitle,
                "primaryTitle", "originalTitle"
        ).build();
    }

    @Override
    public CompletableFuture<List<Movie>> getFuture() {
        return future;
    }

    @Override
    public void accept(List<Movie> returns) {
        future.complete(returns);
    }

    @Override
    public Class<Movie> getInnerClass() {
        return Movie.class;
    }
}

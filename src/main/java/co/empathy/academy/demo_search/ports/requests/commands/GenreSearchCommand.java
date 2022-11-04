package co.empathy.academy.demo_search.ports.requests.commands;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.empathy.academy.demo_search.model.Movie;
import co.empathy.academy.demo_search.ports.queries.PQueryBuilder;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class GenreSearchCommand implements SearchCommand<Movie> {

    private final List<String> genres;
    private final boolean and;
    private CompletableFuture<List<Movie>> future;

    public GenreSearchCommand(List<String> genres) {
        this(genres, true);
    }
    public GenreSearchCommand(List<String> genres, boolean and) {
        this.and = and;
        this.genres = genres;
        this.future = new CompletableFuture<>();
    }

    @Override
    public Query build(PQueryBuilder builder) {
        var must = and ? builder.must() : builder.should();
        for(var genre : genres)
            must = must.match("genres", genre);

        return must.build();
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

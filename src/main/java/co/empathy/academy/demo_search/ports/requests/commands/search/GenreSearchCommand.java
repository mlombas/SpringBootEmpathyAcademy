package co.empathy.academy.demo_search.ports.requests.commands.search;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.empathy.academy.demo_search.model.Title;
import co.empathy.academy.demo_search.ports.queries.PQueryBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class GenreSearchCommand extends DefaultSearchCommand<Title> {

    private final List<String> genres;
    private final boolean and;
    private CompletableFuture<List<Title>> future;

    public GenreSearchCommand(List<String> genres) {
        this(genres, true);
    }
    public GenreSearchCommand(List<String> genres, boolean and) {
        this.and = and;
        this.genres = genres;
        this.future = new CompletableFuture<>();
    }

    @Override
    public Query buildQuery(PQueryBuilder builder) {
        var must = and ? builder.must() : builder.should();
        for(var genre : genres)
            must = must.match("genres", genre);

        return must.build();
    }

    @Override
    public CompletableFuture<List<Title>> getFuture() {
        return future;
    }

    @Override
    public void accept(List<Title> returns) {
        future.complete(returns);
    }

    @Override
    public Class<Title> getInnerClass() {
        return Title.class;
    }
}

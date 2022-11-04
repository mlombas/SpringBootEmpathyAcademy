package co.empathy.academy.demo_search.ports.requests.commands;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.empathy.academy.demo_search.ports.queries.PQueryBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public interface SearchCommand<T> extends Consumer<List<T>> {
    Query build(PQueryBuilder builder);
    CompletableFuture<List<T>> getFuture();
    void accept(List<T> returns);
    Class<T> getInnerClass();
}

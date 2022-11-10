package co.empathy.academy.demo_search.ports.requests.commands.search;

import co.empathy.academy.demo_search.model.Title;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class AllSearchCommand extends DefaultSearchCommand<Title> {
    private CompletableFuture<List<Title>> future
            = new CompletableFuture<>();

    @Override
    public CompletableFuture<List<Title>> getFuture() {
        return future;
    }

    @Override
    public void accept(List<Title> returns) {
        this.future.complete(returns);
    }

    @Override
    public Class<Title> getInnerClass() {
        return Title.class;
    }
}

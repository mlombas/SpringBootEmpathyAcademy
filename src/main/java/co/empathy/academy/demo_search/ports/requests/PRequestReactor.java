package co.empathy.academy.demo_search.ports.requests;

import co.empathy.academy.demo_search.ports.requests.commands.SearchCommand;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Port that represents an adapter capable of reacting to requests
 */
public interface PRequestReactor {
    /**
     * Sends the command to react to
     * @param c the command to send ot the boundary
     */
    public <T> CompletableFuture<List<T>> reactToSearch(SearchCommand<T> c);
}

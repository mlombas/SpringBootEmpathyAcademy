package co.empathy.academy.demo_search.ports.requests;

import co.empathy.academy.demo_search.ports.index.indexer.Indexable;
import co.empathy.academy.demo_search.ports.requests.commands.DocumentCommand;
import co.empathy.academy.demo_search.ports.requests.commands.SearchCommand;
import co.empathy.academy.demo_search.ports.requests.commands.SearchFacetsCommand;
import co.empathy.academy.demo_search.ports.requests.commands.SettingsCommand;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Port that represents an adapter capable of reacting to requests
 */
public interface PRequestReactor {
    /**
     * Sends the command to react to
     * @param c the command to send ot the boundary
     * @return A future list of the thing to search
     */
    public <T> CompletableFuture<List<T>> reactToSearch(SearchCommand<T> c);

    /**
     * Sends the command to react to
     * @param c the command to send out at the boundary
     */
    public <T> void reactToSearchFacet(SearchFacetsCommand<T> c);

    /**
     * Sends the command to react to
     * @param c the command to send out at the boundary
     */
    public <T extends Indexable> void reactToDocument(DocumentCommand<T> c);

    /**
     * Sends the command to react to
     */
    public void reactToSettings(SettingsCommand c);
}

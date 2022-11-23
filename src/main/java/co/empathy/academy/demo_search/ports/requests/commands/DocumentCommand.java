package co.empathy.academy.demo_search.ports.requests.commands;

import java.util.ServiceLoader;

/**
 * Represents the command to index anything
 * @param <T> the thing to index
 */
public interface DocumentCommand<T> {
    /**
     * The documents to index
     * @return an iterable. Prefereable one which doesn't load in
     * memory all documents needed, in case those are too many
     */
    Iterable<T> getDocuments();
}

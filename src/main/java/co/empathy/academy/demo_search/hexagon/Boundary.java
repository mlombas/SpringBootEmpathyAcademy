package co.empathy.academy.demo_search.hexagon;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.empathy.academy.demo_search.ports.executors.PQueryExecutor;
import co.empathy.academy.demo_search.ports.index.IndexerSettings;
import co.empathy.academy.demo_search.ports.index.PDocumentIndexer;
import co.empathy.academy.demo_search.ports.queries.PQueryBuilder;
import co.empathy.academy.demo_search.ports.requests.PRequestReactor;
import co.empathy.academy.demo_search.ports.requests.commands.DocumentCommand;
import co.empathy.academy.demo_search.ports.requests.commands.SearchCommand;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Default boundary.
 * Wires everything together, this is the "hexagon" of our architecture.
 */
@AllArgsConstructor
public class Boundary implements PRequestReactor {
    private PQueryBuilder queryBuilder;
    private PQueryExecutor queryExecutor;

    private PDocumentIndexer indexer;

    @Override
    public <T> CompletableFuture<List<T>> reactToSearch(SearchCommand<T> c) {
        Query q = c.build(queryBuilder);
        c.accept(
                queryExecutor.executeQuery(q, c.getInnerClass())
        );
        return c.getFuture();
    }

    @Override
    public <T> void reactToDocument(DocumentCommand<T> c) {
        c.getDocuments();
    }
}

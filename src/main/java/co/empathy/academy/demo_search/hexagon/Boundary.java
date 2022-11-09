package co.empathy.academy.demo_search.hexagon;

import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.empathy.academy.demo_search.ports.filters.PFilterBuilder;
import co.empathy.academy.demo_search.ports.executors.PQueryExecutor;
import co.empathy.academy.demo_search.ports.index.Indexable;
import co.empathy.academy.demo_search.ports.index.IndexerSettings;
import co.empathy.academy.demo_search.ports.index.PDocumentIndexer;
import co.empathy.academy.demo_search.ports.queries.PQueryBuilder;
import co.empathy.academy.demo_search.ports.requests.PRequestReactor;
import co.empathy.academy.demo_search.ports.requests.commands.DocumentCommand;
import co.empathy.academy.demo_search.ports.requests.commands.SearchCommand;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Default boundary.
 * Wires everything together, this is the "hexagon" of our architecture.
 */
@AllArgsConstructor
public class Boundary implements PRequestReactor {
    private PQueryBuilder queryBuilder;
    private PFilterBuilder filterBuilder;

    private PQueryExecutor queryExecutor;

    private PDocumentIndexer indexer;

    @Override
    public <T> CompletableFuture<List<T>> reactToSearch(SearchCommand<T> c) {
        Query query = c.buildQuery(queryBuilder);
        Query filter =
                c.buildFilter(filterBuilder);
        SearchRequest sr = new SearchRequest.Builder()
                .index("movies")
                .query(query)
                .postFilter(filter)
                .build();
        c.accept(
                queryExecutor.executeQuery(sr, c.getInnerClass())
        );
        return c.getFuture();
    }

    public <T extends Indexable> void reactToDocument(DocumentCommand<T> c) {
        indexer.setSettings(
                new IndexerSettings(
                        20000,
                        "movies"
                )
        );
        indexer.bulkIndex(c.getDocuments());
    }
}

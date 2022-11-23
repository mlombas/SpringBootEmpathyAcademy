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
import co.empathy.academy.demo_search.ports.requests.commands.FacetCommand;
import co.empathy.academy.demo_search.ports.requests.commands.SearchCommand;
import co.empathy.academy.demo_search.ports.requests.commands.SearchFacetsCommand;
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

    @Override
    public <T> void reactToSearchFacet(SearchFacetsCommand<T> c) {
        SearchCommand<?> search = c.getSearch();
        Query query = search.buildQuery(queryBuilder);
        Query filter =
                search.buildFilter(filterBuilder);
        List<SortOptions> order = search.buildOrder(orderBuilder);

        SearchRequest.Builder builder = new SearchRequest.Builder()
                .index("movies")
                .query(query)
                .postFilter(filter)
                .size(search.getMaxNHits())
                .sort(order)
                .aggregations("a", a -> a.histogram(h -> h.field("averageRating").interval(1.0)));

        /*
        for(FacetCommand f : c.getFacets())
            builder.aggregations(f.getName(), f.getFacet());
*/

        SearchRequest sr = builder.build();
        System.out.println(sr);
        c.accept(
                queryExecutor.executeSearchFacetQuery(sr, search.getInnerClass())
        );
    }

    @Override
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

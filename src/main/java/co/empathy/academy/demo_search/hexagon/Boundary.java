package co.empathy.academy.demo_search.hexagon;

import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.empathy.academy.demo_search.ports.filters.PFilterBuilder;
import co.empathy.academy.demo_search.ports.executors.PQueryExecutor;
import co.empathy.academy.demo_search.ports.index.indexer.Indexable;
import co.empathy.academy.demo_search.ports.index.indexer.IndexerSettings;
import co.empathy.academy.demo_search.ports.index.indexer.PDocumentIndexer;
import co.empathy.academy.demo_search.ports.index.settings.PSettingsSetter;
import co.empathy.academy.demo_search.ports.order.POrderBuilder;
import co.empathy.academy.demo_search.ports.queries.PQueryBuilder;
import co.empathy.academy.demo_search.ports.requests.PRequestReactor;
import co.empathy.academy.demo_search.ports.requests.commands.*;
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
    private PFilterBuilder filterBuilder;
    private POrderBuilder orderBuilder;

    private PQueryExecutor queryExecutor;

    private PSettingsSetter setter;
    private PDocumentIndexer indexer;

    @Override
    public <T> CompletableFuture<List<T>> reactToSearch(SearchCommand<T> c) {
        Query query = c.buildQuery(queryBuilder);
        Query filter =
                c.buildFilter(filterBuilder);
        List<SortOptions> order = c.buildOrder(orderBuilder);

        SearchRequest sr = new SearchRequest.Builder()
                .index("movies")
                .query(query)
                .postFilter(filter)
                .size(c.getMaxNHits())
                .sort(order)
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
                .sort(order);

        for(FacetCommand f : c.getFacets())
            builder.aggregations(f.getName(), f.getFacet());

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

    @Override
    public void reactToSettings(SettingsCommand c) {
        c.set(setter);
    }

}

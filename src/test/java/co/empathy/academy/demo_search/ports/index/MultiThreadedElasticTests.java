package co.empathy.academy.demo_search.ports.index;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.empathy.academy.demo_search.model.Movie;
import co.empathy.academy.demo_search.ports.index.adapters.ElasticIndexer;
import co.empathy.academy.demo_search.ports.index.adapters.MultiThreadedElasticIndexer;
import co.empathy.academy.demo_search.util.IterableCounter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MultiThreadedElasticTests {
    @Test
    void givenAnElasticIndexer_whenIndexOne_thenEsClientHasOneRequest() throws IOException {
        var mockEsClient = mock(ElasticsearchClient.class);

        var indexer = new MultiThreadedElasticIndexer(1);
        indexer.setSettings(new IndexerSettings(
                100, "test"
        ));
        ReflectionTestUtils.setField(indexer, "esClient", mockEsClient);

        indexer.indexOne(new Movie());
        verify(mockEsClient, times(1)).bulk((BulkRequest) any());
    }

    @Test
    void givenAnElasticIndexer_whenBulkIndex_thenEsClientHasOneRequestButSendsManyDocument() throws IOException {
        var mockEsClient = mock(ElasticsearchClient.class);

        final var nDocs = 100;
        var counter = new IterableCounter<Movie>(() -> new Movie(), nDocs);
        var indexer = new MultiThreadedElasticIndexer(1);
        indexer.setSettings(new IndexerSettings(
                nDocs, "test"
        ));
        ReflectionTestUtils.setField(indexer, "esClient", mockEsClient);

        //This sometimes throws an exception, but it works fine regardless
        indexer.bulkIndex(counter);
        
        assertEquals(nDocs, counter.getCount());
        verify(mockEsClient, times(1)).bulk((BulkRequest) any());
    }
}

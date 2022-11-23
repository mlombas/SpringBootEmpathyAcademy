package co.empathy.academy.demo_search.ports.index;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.empathy.academy.demo_search.model.titles.Title;
import co.empathy.academy.demo_search.ports.index.indexer.adapters.MultiThreadedElasticIndexer;
import co.empathy.academy.demo_search.ports.index.indexer.IndexerSettings;
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
    void givenAnElasticIndexer_whenIndexOne_thenEsClientHasOneRequest() throws IOException, InterruptedException {
        var mockEsClient = mock(ElasticsearchClient.class);

        var indexer = new MultiThreadedElasticIndexer(1);
        indexer.setSettings(new IndexerSettings(
                100, "test"
        ));
        ReflectionTestUtils.setField(indexer, "esClient", mockEsClient);

        indexer.indexOne(new Title());

        //Wait for termination
        Thread.sleep(100);

        verify(mockEsClient).bulk((BulkRequest) any());
    }

    @Test
    void givenAnElasticIndexer_whenBulkIndex_thenEsClientHasOneRequestButSendsManyDocument() throws IOException, InterruptedException {
        var mockEsClient = mock(ElasticsearchClient.class);

        final var nDocs = 100;
        var counter = new IterableCounter<Title>(() -> new Title(), nDocs);
        var indexer = new MultiThreadedElasticIndexer(1);
        indexer.setSettings(new IndexerSettings(
                nDocs, "test"
        ));
        ReflectionTestUtils.setField(indexer, "esClient", mockEsClient);

        //This sometimes throws an exception, but it works fine regardless
        indexer.bulkIndex(counter);

        Thread.sleep(100);

        assertEquals(nDocs, counter.getCount());
        verify(mockEsClient, times(1)).bulk((BulkRequest) any());
    }
}

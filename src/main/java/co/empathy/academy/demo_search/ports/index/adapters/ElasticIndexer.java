package co.empathy.academy.demo_search.ports.index.adapters;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.empathy.academy.demo_search.ports.index.Indexable;
import co.empathy.academy.demo_search.ports.index.IndexerSettings;
import co.empathy.academy.demo_search.ports.index.PDocumentIndexer;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class ElasticIndexer implements PDocumentIndexer {
    @Autowired
    private ElasticsearchClient esClient;

    private IndexerSettings settings;

    private BulkRequest.Builder requestBuilder;

    public ElasticIndexer() {
        requestBuilder = new BulkRequest.Builder();
    }

    private void flushIndex() throws IOException {
        esClient.bulk(requestBuilder.build());
        requestBuilder = new BulkRequest.Builder();
    }

    private <T extends Indexable> void addDocument(T document) {
        requestBuilder.operations(op -> op.index(idx ->
                idx.index(settings.getIndexName())
                        .id(document.getID())
                        .document(document)
        ));
    }

    @Override
    public <T extends Indexable> void indexOne(T document) {
        addDocument(document);

        try {
            flushIndex();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public <T extends Indexable> void bulkIndex(Iterable<T> documents) {
        long counter = 0;
        int bad = 0;
        for(var document : documents) {
            if(document == null) {
                System.out.println(bad++ + " documents are null");
                continue;
            }

            addDocument(document);

            //Every N items
            if(++counter % settings.getNDocumentsPerBulk() == 0) {
                try {
                    flushIndex();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                System.out.println("N of indexed documents: " + counter);
            }
        }

        //Just in case
        try {
            flushIndex();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setSettings(IndexerSettings s) {
        this.settings = s;
    }
}

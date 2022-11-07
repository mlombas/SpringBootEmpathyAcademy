package co.empathy.academy.demo_search.ports.index.adapters;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.empathy.academy.demo_search.ports.index.Indexable;
import co.empathy.academy.demo_search.ports.index.IndexerSettings;
import co.empathy.academy.demo_search.ports.index.PDocumentIndexer;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MultiThreadedElasticIndexer implements PDocumentIndexer {
    private final int nThreads;

    @Autowired
    private ElasticsearchClient esClient;
    private BulkRequest.Builder builder;
    private IndexerSettings settings;
    private ExecutorService pool;
    private ElasticIndexer subIndexer;

    public MultiThreadedElasticIndexer(int nThreads) {
        this.nThreads = nThreads;
        this.builder = new BulkRequest.Builder();
        this.pool = Executors.newFixedThreadPool(nThreads);
        this.subIndexer = new ElasticIndexer();
    }

    @Override
    public <T extends Indexable> void indexOne(T document) {
        addToBuilder(document);
        flushBuilder();
    }

    @Override
    public <T extends Indexable> void bulkIndex(Iterable<T> documents) {
        int count = 0;
        for(T document : documents) {
            if(document == null) continue;

            addToBuilder(document);
            if(++count % settings.getNDocumentsPerBulk() == 0) {
                flushBuilder();
                System.out.println(count + " documents indexed");
            }
        }

        flushBuilder();
    }

    private void flushBuilder() {
        var passedBuilder = builder;
        pool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    esClient.bulk(passedBuilder.build());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        builder = new BulkRequest.Builder();
    }

    private <T extends Indexable> void addToBuilder(T document) {
        builder.operations(op -> op.index(idx ->
                idx.index(settings.getIndexName())
                        .id(document.getID())
                        .document(document)
                ));
    }

    @Override
    public void setSettings(IndexerSettings s) {
        this.settings = s;
    }
}

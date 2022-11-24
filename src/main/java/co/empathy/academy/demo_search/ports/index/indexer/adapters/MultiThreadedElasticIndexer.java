package co.empathy.academy.demo_search.ports.index.indexer.adapters;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.empathy.academy.demo_search.ports.index.indexer.Indexable;
import co.empathy.academy.demo_search.ports.index.indexer.IndexerSettings;
import co.empathy.academy.demo_search.ports.index.indexer.PDocumentIndexer;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MultiThreadedElasticIndexer implements PDocumentIndexer {
    private final int nThreads;

    @Autowired
    private ElasticsearchClient esClient;
    private IndexerSettings settings;
    private ExecutorService pool;
    private long count;

    private Lock lock;

    public MultiThreadedElasticIndexer(int nThreads) {
        this.nThreads = nThreads;
        this.pool = Executors.newFixedThreadPool(nThreads);

        count = 0;

        lock = new ReentrantLock();
    }

    @Override
    public <T extends Indexable> void indexOne(T document) {
        var builder = addToBuilder(new BulkRequest.Builder(), document);
        try {
            flushBuilder(builder);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T extends Indexable> void bulkIndex(Iterable<T> documents) {
        Iterator<T> iterator = documents.iterator();
        List<T> bulk = new LinkedList<>();
        while(iterator.hasNext()) {
            bulk.add(iterator.next());
            if(bulk.size() >= settings.getNDocumentsPerBulk()) {
                parallelIndex(bulk);
                bulk = new LinkedList<>();
            }
        }

        parallelIndex(bulk);
    }

    private <T extends Indexable> void parallelIndex(List<T> bulk) {
        pool.execute(new Runnable() {
            @Override
            public void run() {
                BulkRequest.Builder builder = new BulkRequest.Builder();
                for(var doc : bulk) builder = addToBuilder(builder, doc);

                lock.lock();
                try {
                    flushBuilder(builder);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                count += bulk.size();
                lock.unlock();

                System.out.println("Indexed " + count + " documents");
            }
        });
    }

    private void flushBuilder(BulkRequest.Builder builder) throws IOException {
        esClient.bulk(builder.build());
    }

    private <T extends Indexable> BulkRequest.Builder addToBuilder(BulkRequest.Builder builder, T document) {
        return builder.operations(op -> op.index(idx ->
                idx.index(settings.getIndexName())
                        .id(document.id())
                        .document(document)
                ));
    }

    @Override
    public void setSettings(IndexerSettings s) {
        this.settings = s;
    }
}

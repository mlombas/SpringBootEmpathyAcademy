package co.empathy.academy.demo_search.ports.index;

import java.util.List;

public interface PDocumentIndexer {
    <T extends Indexable> void indexOne(T document);
    <T extends Indexable> void bulkIndex(Iterable<T> documents);
    void setSettings(IndexerSettings s);
}

package co.empathy.academy.demo_search.ports.index;

import java.util.List;

public interface PDocumentIndexer {
    <T> void indexOne(T document);
    <T> void bulkIndex(List<T> documents);
    void setSettings(IndexerSettings s);
}

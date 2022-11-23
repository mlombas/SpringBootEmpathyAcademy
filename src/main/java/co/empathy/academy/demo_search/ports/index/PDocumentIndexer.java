package co.empathy.academy.demo_search.ports.index;

import java.util.List;

/**
 * Represents an adapter able to index documents
 */
public interface PDocumentIndexer {
    /**
     * Indexes one document at a time
     * @param document the document to index
     * @param <T> any Indexable
     */
    <T extends Indexable> void indexOne(T document);

    /**
     * Indexes documents in bulk. This is, packaging a number of
     * them in a single request
     * @param documents the documents to index
     * @param <T> any Indexable
     */
    <T extends Indexable> void bulkIndex(Iterable<T> documents);

    /**
     * Sets settings for the indexable
     *
     * @see IndexerSettings
     * @param settings the settings
     */
    void setSettings(IndexerSettings settings);
}

package co.empathy.academy.demo_search.ports.index;

import lombok.Data;
import lombok.Value;

/**
 * Settings that an Indexer might need to index documents
 */
@Value
public class IndexerSettings {
    /**
     * Number of documents the Indexer will send in one bulk request
     */
    int nDocumentsPerBulk;

    /**
     * Name of the index the documents are being
     * indexed to
     */
    String indexName;
}

package co.empathy.academy.demo_search.ports.index;

import lombok.Data;
import lombok.Value;

@Value
public class IndexerSettings {
    int nDocumentsPerBulk;
    String indexName;
}

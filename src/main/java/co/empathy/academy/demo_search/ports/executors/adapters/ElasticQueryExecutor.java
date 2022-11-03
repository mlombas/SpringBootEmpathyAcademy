package co.empathy.academy.demo_search.ports.executors.adapters;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.empathy.academy.demo_search.ports.executors.PQueryExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import co.elastic.clients.elasticsearch.core.SearchResponse;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ElasticQueryExecutor implements PQueryExecutor {
    @Autowired
    private ElasticsearchClient esClient;

    @Override
    public <T> List<T> executeQuery(Query query, Class<T> clazz) {
        SearchResponse<T> response;
        try {
            response = esClient.search(s -> s
                    .index("movies")
                    .query(query),
                clazz
            );
        } catch (IOException e) {
            response = null;
        }

        return response.hits().hits().stream()
                .map(Hit::source)
                .collect(Collectors.toList());
    }
}

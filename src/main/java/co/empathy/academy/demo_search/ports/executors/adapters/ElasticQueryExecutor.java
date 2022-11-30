package co.empathy.academy.demo_search.ports.executors.adapters;

import co.elastic.clients.elasticsearch.ElasticsearchClient;

import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.empathy.academy.demo_search.ports.executors.PQueryExecutor;
import co.empathy.academy.demo_search.ports.requests.commands.SearchFacetsCommand;
import org.springframework.beans.factory.annotation.Autowired;
import co.elastic.clients.elasticsearch.core.SearchResponse;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ElasticQueryExecutor implements PQueryExecutor {
    @Autowired
    private ElasticsearchClient esClient;

    private <T> SearchResponse<T> getResponse(SearchRequest sr, Class<T> clazz) {
        SearchResponse<T> response;
        try {
            response = esClient.search(
                    sr,
                    clazz
            );
        } catch (IOException e) {
            System.out.println("Problem while getting response from elastic: " + e.getStackTrace());
            response = null;
        }

        return response;
    }

    @Override
    public <T> List<T> executeQuery(SearchRequest sr, Class<T> clazz) {
        var response = getResponse(sr, clazz);
        return response.hits().hits().stream()
                .map(Hit::source)
                .collect(Collectors.toList());
    }

    @Override
    public <T> SearchFacetsCommand.Result<T> executeSearchFacetQuery(SearchRequest sr, Class<T> clazz) {
        var response = getResponse(sr, clazz);

        return new SearchFacetsCommand.Result<>(
                response.hits().hits().stream()
                        .map(Hit::source)
                        .collect(Collectors.toList()),
                response.aggregations()
                );
    }
}

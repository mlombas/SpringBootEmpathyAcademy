package co.empathy.academy.demo_search.ports.executors.adapters;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
<<<<<<< HEAD
<<<<<<< Updated upstream
=======
import co.elastic.clients.elasticsearch._types.aggregations.Aggregate;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
>>>>>>> Stashed changes
=======
import co.elastic.clients.elasticsearch._types.aggregations.Aggregate;
>>>>>>> main
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.empathy.academy.demo_search.model.Title;
import co.empathy.academy.demo_search.ports.executors.PQueryExecutor;
import co.empathy.academy.demo_search.ports.requests.commands.SearchFacetsCommand;
import org.springframework.beans.factory.annotation.Autowired;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
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

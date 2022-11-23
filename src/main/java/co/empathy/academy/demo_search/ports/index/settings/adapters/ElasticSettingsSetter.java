package co.empathy.academy.demo_search.ports.index.settings.adapters;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.empathy.academy.demo_search.ports.index.settings.PSettingsSetter;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStream;

@RequiredArgsConstructor
public class ElasticSettingsSetter implements PSettingsSetter {
    private final String indexName;

    @Autowired
    private ElasticsearchClient esClient;

    @Override
    public void setSettings(InputStream settings) {
        try {
            esClient.indices().putSettings(p -> p.index(indexName).withJson(settings));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setMapping(InputStream mapping) {
        try {
            esClient.indices().putMapping(p -> p.index(indexName).withJson(mapping));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

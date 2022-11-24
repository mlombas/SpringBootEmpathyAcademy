package co.empathy.academy.demo_search.configuration;

import co.empathy.academy.demo_search.hexagon.Boundary;
import co.empathy.academy.demo_search.ports.executors.PQueryExecutor;
import co.empathy.academy.demo_search.ports.executors.adapters.ElasticQueryExecutor;
import co.empathy.academy.demo_search.ports.filters.PFilterBuilder;
import co.empathy.academy.demo_search.ports.filters.adapters.ElasticFilterBuilder;
import co.empathy.academy.demo_search.ports.index.indexer.PDocumentIndexer;
import co.empathy.academy.demo_search.ports.index.indexer.adapters.MultiThreadedElasticIndexer;
import co.empathy.academy.demo_search.ports.index.settings.PSettingsSetter;
import co.empathy.academy.demo_search.ports.index.settings.adapters.ElasticSettingsSetter;
import co.empathy.academy.demo_search.ports.order.POrderBuilder;
import co.empathy.academy.demo_search.ports.order.adapters.ElasticOrderBuilder;
import co.empathy.academy.demo_search.ports.queries.PQueryBuilder;
import co.empathy.academy.demo_search.ports.queries.adapters.ElasticQueryBuilder;
import co.empathy.academy.demo_search.ports.requests.PRequestReactor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public PQueryExecutor queryExecutor() {
        return new ElasticQueryExecutor();
    }

    @Bean
    public PQueryBuilder queryBuilder() {
        return new ElasticQueryBuilder();
    }
    @Bean
    public PFilterBuilder filterBuilder() {
        return new ElasticFilterBuilder();
    }
    @Bean
    public POrderBuilder orderBuilder() {
        return new ElasticOrderBuilder();
    }

    @Bean
    public PSettingsSetter settingsSetter() { return new ElasticSettingsSetter("movies");
    }
    @Bean
    public PDocumentIndexer documentIndexer() { return new MultiThreadedElasticIndexer(16);}
    @Bean
    public PRequestReactor requestReactor(
            PQueryBuilder qbuilder, PFilterBuilder fbuilder, POrderBuilder obuilder,
            PQueryExecutor executor, PSettingsSetter setter, PDocumentIndexer indexer
    ) {
        return new Boundary(qbuilder, fbuilder, obuilder, executor, setter, indexer);
    }
}

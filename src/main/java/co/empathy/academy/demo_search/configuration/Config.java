package co.empathy.academy.demo_search.configuration;

import co.empathy.academy.demo_search.hexagon.Boundary;
import co.empathy.academy.demo_search.ports.executors.PQueryExecutor;
import co.empathy.academy.demo_search.ports.executors.adapters.ElasticQueryExecutor;
import co.empathy.academy.demo_search.ports.queries.PQueryBuilder;
import co.empathy.academy.demo_search.ports.queries.adapters.ElasticQueryBuilder;
import co.empathy.academy.demo_search.ports.requests.PRequestReactor;
import org.springframework.beans.factory.annotation.Value;
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
    public PRequestReactor requestReactor(PQueryBuilder builder, PQueryExecutor executor) {
        return new Boundary(builder, executor);
    }
}

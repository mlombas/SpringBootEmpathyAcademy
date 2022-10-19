package co.empathy.academy.demo_search.configuration;

import co.empathy.academy.demo_search.services.ElasticEngine;
import co.empathy.academy.demo_search.services.SearchEngine;
import co.empathy.academy.demo_search.services.SearchService;
import co.empathy.academy.demo_search.services.SearchServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public SearchEngine searchEngine() {
        return new ElasticEngine();
    }

    @Bean
    public SearchService searchService(SearchEngine engine) {
        return new SearchServiceImpl(engine);
    }
}

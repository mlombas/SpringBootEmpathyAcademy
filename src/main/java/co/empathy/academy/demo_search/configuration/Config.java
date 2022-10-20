package co.empathy.academy.demo_search.configuration;

import co.empathy.academy.demo_search.services.ElasticEngine;
import co.empathy.academy.demo_search.services.SearchEngine;
import co.empathy.academy.demo_search.services.SearchService;
import co.empathy.academy.demo_search.services.SearchServiceImpl;
import co.empathy.academy.demo_search.services.RestService;
import co.empathy.academy.demo_search.services.RestServiceImpl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public SearchEngine searchEngine(RestService rest) {
        return new ElasticEngine(rest);
    }

    @Bean
    public SearchService searchService(SearchEngine engine) {
        return new SearchServiceImpl(engine);
    }

    @Bean
    public RestService restService() {
	return new RestServiceImpl();
    }
}

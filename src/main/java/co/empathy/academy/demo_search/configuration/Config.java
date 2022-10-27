package co.empathy.academy.demo_search.configuration;

import co.empathy.academy.demo_search.services.*;

import org.json.JSONObject;
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

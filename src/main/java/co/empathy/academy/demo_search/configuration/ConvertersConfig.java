package co.empathy.academy.demo_search.configuration;

import co.empathy.academy.demo_search.configuration.converters.OrderConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ConvertersConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new OrderConverter());
    }
}

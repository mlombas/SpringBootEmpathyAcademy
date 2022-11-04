package co.empathy.academy.demo_search.ports.queries;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.empathy.academy.demo_search.ports.executors.adapters.ElasticQueryExecutor;
import co.empathy.academy.demo_search.ports.queries.adapters.ElasticQueryBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class ElasticQueryBuilderTests {

    @Test
    void givenFreshObject_whenMustQuerWithInsides_mustQueryIsReturned() {
        ElasticQueryBuilder builder = new ElasticQueryBuilder();
        builder.must().match("a", "b");
        Query result = builder.build();

        assertTrue(result.isBool());
        assertTrue(result.toString().contains("must"));
    }

    @Test
    void givenFreshObject_whenShouldQuery_shouldQueryIsReturned() {
        ElasticQueryBuilder builder = new ElasticQueryBuilder();
        builder.should().match("a", "b");
        Query result = builder.build();

        assertTrue(result.isBool());
        assertTrue(result.toString().contains("should"));
    }

    @Test
    void givenFreshObject_whenMultiMatchQuery_multiMatchQueryIsReturned() {
        ElasticQueryBuilder builder = new ElasticQueryBuilder();
        builder.multi("a", "b");
        Query result = builder.build();

        assertTrue(result.isMultiMatch());
    }

    @Test
    void givenNonFreshObject_whenBuild_objectResets() {
        ElasticQueryBuilder builder = new ElasticQueryBuilder();
        builder.must();
        builder.build();

        builder.multi("a", "b");
        Query result = builder.build();

        assertFalse(result.isBool());
        assertTrue(result.isMultiMatch());
    }
}

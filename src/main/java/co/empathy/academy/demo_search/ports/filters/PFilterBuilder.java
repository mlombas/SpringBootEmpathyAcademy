package co.empathy.academy.demo_search.ports.filters;

import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.With;

import java.util.Map;

public interface PFilterBuilder {
    PFilterBuilder range(String field, String to, String from);

    Query build();
}

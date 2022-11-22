package co.empathy.academy.demo_search.model;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.With;

@Value
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class Aka {
    String title;
    String region;
    String language;
    Boolean isOriginalLanguage;
}

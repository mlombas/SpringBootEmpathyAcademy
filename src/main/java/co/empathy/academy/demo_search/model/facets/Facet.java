package co.empathy.academy.demo_search.model.facets;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.With;

import java.util.Arrays;
import java.util.List;

@Value
@NoArgsConstructor(force = true)
@AllArgsConstructor
@With
public class Facet {
    @lombok.Value
    public static class Value {
        String id;
        String value;
        Long count;
        String filter;
    }

    String facet;
    String type;
    List<Value> values;
}

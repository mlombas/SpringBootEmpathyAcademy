package co.empathy.academy.demo_search.ports.filters;

import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.With;

import java.util.Map;

/**
 * Port that represents an adapter capable of build query filters
 */
public interface PFilterBuilder {

    /**
     * Range filter, excludes any value in the filed
     * strictly outside it
     *
     * @param field the field to check
     * @param to minimum value of the field
     * @param from maximum value of the vield
     * @return another filter builder, for fluid interface
     */
    PFilterBuilder range(String field, Object to, Object from);

    /**
     * Match filter, excludes any value in the field
     * not exactly equal to the value passed in
     *
     * @param field the field to check
     * @param value the value to match
     * @return another filter builder, for fluid interface
     */
    PFilterBuilder match(String field, String value);

    /**
     * Builds the filter, which becomes a Query
     * @return the filter query
     */
    Query build();
}

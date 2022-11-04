package co.empathy.academy.demo_search.ports.queries;


import co.elastic.clients.elasticsearch._types.query_dsl.Query;

import java.util.List;

/**
 * Port that represents an adapter capable of building queries.
 */
public interface PQueryBuilder {
    /**
     * Starts a "must" type query
     * @return PQueryBuilder for fluid interface
     */
    PQueryBuilder must();
    /**
     * Starts a "should" type query
     * @return PQueryBuilder for fluid interface
     */
    PQueryBuilder should();

    /**
     * Starts a "match" type query
     * @return PQueryBuilder for fluid interface
     */
    PQueryBuilder match(String field, String value);

    /**
     * Starts a "multimatch" type query
     * @return PQueryBuilder for fluid interface
     */
    PQueryBuilder multi(String query, List<String> fields);
    /**
     * Starts a "multimatch" type query
     * @return PQueryBuilder for fluid interface
     */
    PQueryBuilder multi(String query, String... fields);

    /**
     * Builds the query
     * @return the query built
     */
    Query build();
}

package co.empathy.academy.demo_search.ports.queries.adapters;

public class ElasticStateException extends RuntimeException {
    public ElasticStateException(String stateName, String methodName) {
        super(stateName + " does no support method " + methodName);
    }
}

package co.empathy.academy.demo_search.ports.queries.adapters;

public abstract class DefaultState implements ElasticQueryBuilderState {
    @Override
    public ElasticQueryBuilderState must() {
        throw new ElasticStateException(this.getClass().getName(), "must");
    }
    @Override
    public ElasticQueryBuilderState should() {
        throw new ElasticStateException(this.getClass().getName(), "should");
    }

    @Override
    public ElasticQueryBuilderState match(String field, String value) {
        throw new ElasticStateException(this.getClass().getName(), "match");
    }
}

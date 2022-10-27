package co.empathy.academy.demo_search.query;

public class MainQuery extends SingleCompoundQuery {
    public MainQuery(Query compound) {
        super(compound);
    }

    @Override
    protected String queryName() {
        return "query";
    }
}

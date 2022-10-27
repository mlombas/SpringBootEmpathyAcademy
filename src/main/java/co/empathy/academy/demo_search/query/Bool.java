package co.empathy.academy.demo_search.query;

public class Bool extends SingleCompoundQuery {

    public Bool(Query compound) {
        super(compound);
    }

    @Override
    protected String queryName() {
        return "bool";
    }
}

package co.empathy.academy.demo_search.query;

public class And extends MultipleCompoundQuery {
    public And(Query... compounds) {
        for(var compound : compounds)
            addCompound(compound);
    }

    @Override
    protected String queryName() {
        return "must";
    }
}

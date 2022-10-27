package co.empathy.academy.demo_search.query;

public class Or extends MultipleCompoundQuery {

    public Or(Query... compounds) {
        for(var compound : compounds)
            addCompound(compound);
    }

    @Override
    protected String queryName() {
        return "should";
    }
}

package co.empathy.academy.demo_search.query;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class MultipleCompoundQuery implements Query {
    protected List<Query> compounds;

    public MultipleCompoundQuery() {
        compounds = new LinkedList<>();
    }

    public MultipleCompoundQuery addCompound(Query q) {
        compounds.add(q);

        return this;
    }

    protected abstract String queryName();

    @Override
    public JSONObject make() {
        return new JSONObject()
                .put(
                        queryName(),
                        new JSONArray(
                                compounds.stream()
                                        .map(Query::make)
                                        .collect(Collectors.toList())
                        )
                );
    }
}

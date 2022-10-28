package co.empathy.academy.demo_search.query;

import org.json.JSONObject;

public class LeafQuery implements Query {
    private String name;
    private Object value;

    public LeafQuery(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public JSONObject make() {
       return new JSONObject()
               .put(name, value);
    }
}

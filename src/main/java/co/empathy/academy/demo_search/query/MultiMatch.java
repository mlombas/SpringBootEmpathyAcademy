package co.empathy.academy.demo_search.query;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class MultiMatch implements Query {
    private String query;
    private List<String> fields;

    public MultiMatch(String query, String... fields) {
        this.query = query;
        this.fields = Arrays.stream(fields).toList();
    }

    @Override
    public JSONObject make() {
        return new JSONObject().put("multi_match",
                new JSONObject()
                        .put("query", query)
                        .put("fields", new JSONArray(fields))
        );
    }
}

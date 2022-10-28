package co.empathy.academy.demo_search.services;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public interface SearchEngine {
    public String getVersion();
    public String echo(String query);

    public void postDocument(JSONObject obj);

    public JSONArray searchGenre(List<String> genres);
    public JSONArray searchGenre(List<String> genres, boolean and);
}

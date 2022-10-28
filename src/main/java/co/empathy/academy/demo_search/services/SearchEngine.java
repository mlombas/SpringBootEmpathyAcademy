package co.empathy.academy.demo_search.services;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public interface SearchEngine {
    String getVersion();
    String echo(String query);

    void postDocument(JSONObject obj);

    JSONArray searchGenre(List<String> genres);
    JSONArray searchGenre(List<String> genres, boolean and);

    JSONArray searchTitle(String inTitle);
}

package co.empathy.academy.demo_search.model;

import lombok.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Value
@With
public class Movie {
    private UUID id;
    private String type;
    private String primaryTitle;
    private String originalTitle;
    private boolean isAdult;
    private String startYear;
    private String endYear;
    private int runtimeMinutes;
    private List<String> genres;

    private String nonNullOrEmpty(String s) {
        if(s == null) return "";
        return s;
    }

    public JSONObject toJSON() {
        return new JSONObject()
                .put("id", id)
                .put("type", nonNullOrEmpty(type))
                .put("primaryTitle", nonNullOrEmpty(primaryTitle))
                .put("originalTitle", nonNullOrEmpty(originalTitle))
                .put("isAdult", isAdult)
                .put("startYear", nonNullOrEmpty(startYear))
                .put("endYear", nonNullOrEmpty(endYear))
                .put("runtimeMinutes", runtimeMinutes)
                .put("genres", new JSONArray(genres));
    }

    public static Movie make(String res) {
        return make(new JSONObject(res));
    }
    public static Movie make(JSONObject json) {
        JSONArray genres =  json.optJSONArray("genres");
        List<String> genresList;
        if(genres != null)
            genresList = genres
                    .toList().stream()
                    .map(element -> (String) element)
                    .map(String::toLowerCase)
                    .collect(Collectors.toList());
        else genresList = new LinkedList<>();

        return new Movie(
                (UUID) json.opt("id"),
                json.optString("type"),
                json.optString("primaryTitle"),
                json.optString("originalTitle"),
                json.optBoolean("isAdult"),
                json.optString("startYear"),
                json.optString("endYear"),
                json.optInt("runtimeMinutes"),
               genresList
        );
    }
}

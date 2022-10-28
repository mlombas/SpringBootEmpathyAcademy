package co.empathy.academy.demo_search.model;

import lombok.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Movie {
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
        var movie = new Movie();

        if(json.has("type"))
            movie.type = json.getString("type");
        if(json.has("primaryTitle"))
            movie.primaryTitle = json.getString("primaryTitle");
        if(json.has("originalTitle"))
            movie.originalTitle = json.getString("originalTitle");
        if(json.has("isAdult"))
            movie.isAdult = json.getBoolean("isAdult");
        if(json.has("startYear"))
            movie.startYear = json.getString("startYear");
        if(json.has("endYear"))
            movie.endYear = json.getString("endYear");
        if(json.has("runtimeMinutes"))
            movie.runtimeMinutes = json.getInt("runtimeMinutes");
        if(json.has("genres"))
            movie.genres = json.getJSONArray("genres")
                    .toList().stream()
                    .map(element -> (String) element)
                    .map(String::toLowerCase)
                    .collect(Collectors.toList());

        return movie;
    }
}

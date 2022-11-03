package co.empathy.academy.demo_search.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor(force = true)
@Value
public class Movie {
    private String type;
    private String primaryTitle;
    private String originalTitle;
    private boolean adult;
    private String startYear;
    @Nullable private String endYear;
    private int runtimeMinutes;
    private List<String> genres;
}

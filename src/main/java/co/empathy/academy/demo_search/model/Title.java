package co.empathy.academy.demo_search.model;

import co.empathy.academy.demo_search.ports.index.Indexable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.annotation.Nullable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor(force = true)
@Value
@With
@JsonIgnoreProperties(
        //Neccesary as for id method of interface "Indexable"
        value = { "id" }
)
public class Title implements Indexable {
    private String tconst;
    private String titleType;
    private String primaryTitle;
    private String originalTitle;
    private Boolean isAdult;
    private String startYear;
    @Nullable private String endYear;
    @Nullable private Integer runtimeMinutes;
    private List<String> genres;
    @Nullable Double averageRating;
    @Nullable Integer numVotes;

    @Override
    public String id() {
        return tconst;
    }
}

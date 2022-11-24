package co.empathy.academy.demo_search.model.titles;

import co.empathy.academy.demo_search.ports.index.indexer.Indexable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
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

    @Nullable List<Aka> akas;

    @Nullable List<Directors> directors;
    @Nullable List<Writers> writers;

    @Nullable List<Starring> starring;
    
    public Title() {
        tconst = null;
        titleType = null;
        primaryTitle = null;
        originalTitle = null;
        isAdult = null;
        startYear = null;
        endYear = null;
        genres = null;

        runtimeMinutes = 0;
        averageRating = 0.;
        numVotes = 0;

        akas = new ArrayList<>();

        directors = new ArrayList<>();
        writers = new ArrayList<>();

        starring = new ArrayList<>();
    }

    @Override
    public String id() {
        return tconst;
    }

    public Title withOneMoreAka(Aka aka) {
        akas.add(aka);
        return this;
    }

    public Title withOneMoreDirector(Directors d) {
        directors.add(d);
        return this;
    }
    public Title withOneMoreWriter(Writers w) {
        writers.add(w);
        return this;
    }

    public Title withOneMoreStarring(Starring starring) {
        this.starring.add(starring);
        return this;
    }
}

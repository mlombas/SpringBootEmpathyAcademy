package co.empathy.academy.demo_search.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.annotation.Nullable;

@AllArgsConstructor
@NoArgsConstructor(force = true)
@Value
public class Ratings {
    String tconst;
    @Nullable Double averageRating;
    @Nullable Integer numVotes;
}

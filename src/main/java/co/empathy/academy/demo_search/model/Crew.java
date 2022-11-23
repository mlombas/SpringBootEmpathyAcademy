package co.empathy.academy.demo_search.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class Crew {
    String tconst;
    String directors;
    String writers;

    public Directors getDirectors() {
        return new Directors(directors);
    }

    public Writers getWriters() {
        return new Writers(writers);
    }
}

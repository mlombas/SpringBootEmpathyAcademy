package co.empathy.academy.demo_search.model.titles;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class Principals {
    String tconst;
    String nconst;
    String characters;

    public Starring getStarring() {
        return new Starring(new Starring.Name(nconst), characters);
    }
}

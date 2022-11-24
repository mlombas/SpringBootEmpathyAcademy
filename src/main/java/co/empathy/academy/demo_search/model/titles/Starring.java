package co.empathy.academy.demo_search.model.titles;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Starring {
    @Value
    @NoArgsConstructor(force = true)
    @AllArgsConstructor
    public static class Name {
        String nconst;
    }

    Name name;
    String characters;
}

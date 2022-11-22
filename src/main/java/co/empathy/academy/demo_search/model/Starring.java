package co.empathy.academy.demo_search.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Starring {
    @Value
    public static class Name {
        String nconst;
    }

    Name name;
    String characters;
}

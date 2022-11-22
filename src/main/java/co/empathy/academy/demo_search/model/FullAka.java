package co.empathy.academy.demo_search.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.With;

@Value
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class FullAka {
    String titleId;
    String ordering;

    String title;
    String region;
    String language;
    Boolean isOriginalLanguage;

    public Aka getBaseAka() {
        return new Aka(
                title,
                region,
                language,
                isOriginalLanguage
        );
    }
}

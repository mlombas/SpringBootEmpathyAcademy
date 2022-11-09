package co.empathy.academy.demo_search.ports.requests.commands.search;

import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.With;

@Value
@With
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class SearchFilters {
    String genre;
    Integer durationMin;
    Integer durationMax;
    Integer yearMin;
    Integer yearMax;
}

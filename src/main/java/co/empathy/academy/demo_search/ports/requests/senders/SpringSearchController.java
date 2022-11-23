package co.empathy.academy.demo_search.ports.requests.senders;

import co.empathy.academy.demo_search.model.Movie;
import co.empathy.academy.demo_search.ports.requests.PRequestReactor;
<<<<<<< Updated upstream
import co.empathy.academy.demo_search.ports.requests.commands.search.GenreSearchCommand;
import co.empathy.academy.demo_search.ports.requests.commands.search.InTitleSearchCommand;
import co.empathy.academy.demo_search.ports.requests.commands.search.SearchFilters;
import io.swagger.v3.oas.models.security.SecurityScheme;
=======
import co.empathy.academy.demo_search.ports.requests.commands.SearchFacetsCommand;
import co.empathy.academy.demo_search.ports.requests.commands.facets.ElasticTermsAggregation;
import co.empathy.academy.demo_search.ports.requests.commands.search.AllSearchCommand;
import co.empathy.academy.demo_search.ports.requests.commands.search.GenreSearchCommand;
import co.empathy.academy.demo_search.ports.requests.commands.search.InTitleSearchCommand;
import co.empathy.academy.demo_search.ports.requests.commands.search.SearchFilters;
import co.empathy.academy.demo_search.ports.requests.commands.searchfacets.BasicSearchFacetsCommand;
import co.empathy.academy.demo_search.ports.requests.senders.util.SearchResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
>>>>>>> Stashed changes
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLSyntaxErrorException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/search")
public class SpringSearchController {

    @Autowired
    private PRequestReactor reactor;

<<<<<<< Updated upstream
=======
    @Operation(summary = "Search for all movies satisfying a set of filters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of movies found, it can be empty"),
            @ApiResponse(responseCode = "500", description = "Error searching the movies")
    })
    @Parameters(value = {
            @Parameter(name = "genres", required = false, description = "List of genres to match"),

            @Parameter(name = "minYear", required = false, description = "Minimum year to match"),
            @Parameter(name = "maxYear", required = false, description = "Maximum year to match"),

            @Parameter(name = "minMinutes", required = false, description = "Minimum runtime minutes to match"),
            @Parameter(name = "maxMinutes", required = false, description = "Maximum runtime minutes to match"),

            @Parameter(name = "minScore", required = false, description = "Minimum score to match"),
            @Parameter(name = "maxScore", required = false, description = "Maximum score to match"),

            @Parameter(name = "type", required = false, description = "Type of title to match"),
    })
    @GetMapping("/")
    public CompletableFuture<ResponseEntity<Map<String, Object>>>
    search(
            @Nullable @RequestParam List<String> genre,

            @Nullable @RequestParam Integer minYear,
            @Nullable @RequestParam Integer maxYear,

            @Nullable @RequestParam Integer minMinutes,
            @Nullable @RequestParam Integer maxMinutes,

            @Nullable @RequestParam Float minScore,
            @Nullable @RequestParam Float maxScore,

            @Nullable @RequestParam String type,

            @Nullable @RequestParam Integer maxNHits,

            @Nullable @RequestParam POrderBuilder.Order sortRating
    )
    {

        var search = new AllSearchCommand(
                        Optional.ofNullable(genre),

                        Optional.ofNullable(minYear),
                        Optional.ofNullable(maxYear),

                        Optional.ofNullable(minMinutes),
                        Optional.ofNullable(maxMinutes),

                        Optional.ofNullable(minScore),
                        Optional.ofNullable(maxScore),

                        Optional.ofNullable(type),

                        Optional.ofNullable(sortRating),

                        Optional.ofNullable(maxNHits).orElse(10)
                );

        var command = new BasicSearchFacetsCommand<>(
                search,
                new ElasticTermsAggregation("genres")
        );
        reactor.reactToSearchFacet(command);

        return command.getFuture()
                .thenApply(result -> {
                    var response = new HashMap();
                    response.put("hits", result.getHits());

                    response.put("facets", result.getAggregates());
                    return response;
                })
                .thenApply(response -> ResponseEntity.ok(response));
    }

>>>>>>> Stashed changes
    @GetMapping("/genres/{and}")
    public CompletableFuture<ResponseEntity<List<Movie>>>
    genres(@PathVariable boolean and, @RequestBody List<String> genres)
    {
        CompletableFuture<List<Movie>> movies = reactor.reactToSearch(
                new GenreSearchCommand(genres, and)
        );

        return movies
                .thenApply(m ->
                        ResponseEntity.ok(m)
                );
    }
    @GetMapping("/genres")
    public CompletableFuture<ResponseEntity<List<Movie>>>
    genres(@RequestBody List<String> genres)
    {
        return genres(true, genres);
    }

    @GetMapping("/intitle")
    public CompletableFuture<ResponseEntity<List<Movie>>>
    intitle(
            @RequestBody String intitle,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) Integer durationMin,
            @RequestParam(required = false) Integer durationMax,
            @RequestParam(required = false) Integer yearMin,
            @RequestParam(required = false) Integer yearMax
            ) {
        var filters = new SearchFilters()
                .withGenre(genre)
                .withDurationMin(durationMin)
                .withDurationMax(durationMax)
                .withYearMin(yearMin)
                .withYearMax(yearMax);
        return reactor.reactToSearch(
                        new InTitleSearchCommand(intitle, filters)
                )
                .thenApply(m ->
                        ResponseEntity.ok(m)
                );
    }
}
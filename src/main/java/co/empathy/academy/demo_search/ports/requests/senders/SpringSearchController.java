package co.empathy.academy.demo_search.ports.requests.senders;

import co.elastic.clients.elasticsearch._types.aggregations.Aggregate;
import co.empathy.academy.demo_search.model.facets.Facet;
import co.empathy.academy.demo_search.model.titles.Title;
import co.empathy.academy.demo_search.ports.order.POrderBuilder;
import co.empathy.academy.demo_search.ports.requests.PRequestReactor;
import co.empathy.academy.demo_search.ports.requests.commands.search.GenreSearchCommand;
import co.empathy.academy.demo_search.ports.requests.commands.search.InTitleSearchCommand;
import co.empathy.academy.demo_search.ports.requests.commands.search.SearchFilters;
import co.empathy.academy.demo_search.ports.requests.commands.facets.ElasticTermsAggregation;
import co.empathy.academy.demo_search.ports.requests.commands.search.AllSearchCommand;
import co.empathy.academy.demo_search.ports.requests.commands.searchfacets.BasicSearchFacetsCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/search")
public class SpringSearchController {

    @Autowired
    private PRequestReactor reactor;

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

                    System.out.println(result.getAggregates());

                    var facets = new ArrayList<Facet>();
                    response.put("facets",
                            new Facet()
                                    .withFacet("genres")
                                    .withType("values")
                                    .withValues(
                                            ((Aggregate) result.getAggregates().get("genres"))
                                                    .sterms().buckets().array().stream().map(b ->
                                                        new Facet.Value(
                                                                b.key().toLowerCase(),
                                                                b.key().toLowerCase(),
                                                                b.docCount(),
                                                                b.key().toLowerCase()
                                                        )
                                                    ).collect(Collectors.toList())
                                    )

                    );
                    return response;
                })
                .thenApply(response -> ResponseEntity.ok(response));
    }

    @GetMapping("/genres/{and}")
    public CompletableFuture<ResponseEntity<List<Title>>>
    genres(@PathVariable boolean and, @RequestBody List<String> genres)
    {
        CompletableFuture<List<Title>> movies = reactor.reactToSearch(
                new GenreSearchCommand(genres, and)
        );

        return movies
                .thenApply(m ->
                        ResponseEntity.ok(m)
                );
    }
    @GetMapping("/genres")
    public CompletableFuture<ResponseEntity<List<Title>>>
    genres(@RequestBody List<String> genres)
    {
        return genres(true, genres);
    }

    @GetMapping("/intitle")
    public CompletableFuture<ResponseEntity<List<Title>>>
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
package co.empathy.academy.demo_search.ports.requests.senders;

import co.empathy.academy.demo_search.model.Title;
import co.empathy.academy.demo_search.ports.requests.PRequestReactor;
import co.empathy.academy.demo_search.ports.requests.commands.search.AllSearchCommand;
import co.empathy.academy.demo_search.ports.requests.commands.search.GenreSearchCommand;
import co.empathy.academy.demo_search.ports.requests.commands.search.InTitleSearchCommand;
import co.empathy.academy.demo_search.ports.requests.commands.search.SearchFilters;
import co.empathy.academy.demo_search.ports.requests.senders.util.SearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nullable;
import javax.validation.constraints.Null;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/search")
public class SpringSearchController {

    @Autowired
    private PRequestReactor reactor;

    @GetMapping
    public CompletableFuture<ResponseEntity<Map<String, Object>>>
    search(
            @Nullable @RequestParam List<String> genres,

            @Nullable @RequestParam Integer minYear,
            @Nullable @RequestParam Integer maxYear,

            @Nullable @RequestParam Integer minMinutes,
            @Nullable @RequestParam Integer maxMinutes,

            @Nullable @RequestParam Float minScore,
            @Nullable @RequestParam Float maxScore,

            @Nullable @RequestParam String type
    )
    {
        CompletableFuture<List<Title>> titles = reactor.reactToSearch(
                new AllSearchCommand(
                        Optional.ofNullable(genres),

                        Optional.ofNullable(minYear),
                        Optional.ofNullable(maxYear),

                        Optional.ofNullable(minMinutes),
                        Optional.ofNullable(maxMinutes),

                        Optional.ofNullable(minScore),
                        Optional.ofNullable(maxScore),

                        Optional.ofNullable(type)
                )
        );

        return titles
                .thenApply(hits -> {
                    var response = new HashMap();
                    response.put("hits", hits);
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
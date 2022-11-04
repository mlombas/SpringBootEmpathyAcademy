package co.empathy.academy.demo_search.ports.requests.senders;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.empathy.academy.demo_search.model.Movie;
import co.empathy.academy.demo_search.ports.queries.PQueryBuilder;
import co.empathy.academy.demo_search.ports.requests.PRequestReactor;
import co.empathy.academy.demo_search.ports.requests.commands.GenreSearchCommand;
import co.empathy.academy.demo_search.ports.requests.commands.InTitleSearchCommand;
import co.empathy.academy.demo_search.ports.requests.commands.SearchCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/search")
public class SpringSearchController {

    @Autowired
    private PRequestReactor reactor;

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
    intitle(@RequestBody String intitle) {
        return reactor.reactToSearch(
                        new InTitleSearchCommand(intitle)
                )
                .thenApply(m ->
                        ResponseEntity.ok(m)
                );
    }
}
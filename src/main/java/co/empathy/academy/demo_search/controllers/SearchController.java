package co.empathy.academy.demo_search.controllers;

import co.empathy.academy.demo_search.model.Movie;
import co.empathy.academy.demo_search.services.SearchService;
import co.empathy.academy.demo_search.services.SearchServiceImpl;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {
    @Autowired
    private SearchService service;

    @GetMapping("/genres/{and}")
    public ResponseEntity<List<Movie>> genres(@PathVariable boolean and, @RequestBody List<String> genres) {
        return ResponseEntity.ok(
                service.searchGenres(
                        genres,
                        and
                )
        );
    }
    @GetMapping("/genres")
    public ResponseEntity<List<Movie>> genres(@RequestBody List<String> genres) {
        return ResponseEntity.ok(
                service.searchGenres(
                genres
                )
        );
    }

    @GetMapping("/intitle")
    public ResponseEntity<List<Movie>> intitle(@RequestBody String intitle) {
        return ResponseEntity.ok(
                service.searchTitle(intitle)
        );
    }
}

package co.empathy.academy.demo_search.controllers;

import co.empathy.academy.demo_search.model.Movie;
import co.empathy.academy.demo_search.services.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/document")
public class DocumentController {
    @Autowired
    private SearchService service;

    @PostMapping("/add")
    public ResponseEntity<Movie> document(@RequestBody Movie movie) {
        UUID id = service.postMovie(movie).getId();
        return ResponseEntity.created(
                URI.create(null) // TODO: fill in this placeholder
        ).build();
    }
}

package co.empathy.academy.demo_search.services;

import co.empathy.academy.demo_search.model.Movie;

import java.util.List;

public interface SearchService {
    String engineVersion();
    String echo(String query);

    void postMovie(Movie m);

    List<Movie> searchGenres(String document);
    List<Movie> searchGenres(String document, boolean and);
}

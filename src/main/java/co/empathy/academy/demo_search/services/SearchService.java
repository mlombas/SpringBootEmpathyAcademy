package co.empathy.academy.demo_search.services;

import co.empathy.academy.demo_search.model.Movie;

public interface SearchService {
    public String engineVersion();
    public String echo(String query);

    public void postMovie(Movie m);

    void searchGenres(String document);
}

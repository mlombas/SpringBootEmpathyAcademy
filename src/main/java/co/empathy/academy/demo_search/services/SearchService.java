package co.empathy.academy.demo_search.services;

import co.empathy.academy.demo_search.model.Movie;

import java.util.List;

public interface SearchService {
    /**
     * @return the search engine version
     */
    String engineVersion();

    /**
     * Echoes whatever is sent
     *
     * @param query the thing to echo
     * @return query
     */
    String echo(String query);

    /**
     * Posts a movie to the search
     *
     * @param m the movie
     */
    void postMovie(Movie m);

    /**
     * Searches movies, returning those that match all genres passed in
     *
     * @param documents a list of genres to check against
     * @return all movies that have all genres passed in
     */
    List<Movie> searchGenres(List<String> documents);
    /**
     * Searches movies, returning those that match all or any genres passed in,
     * based on the second parameter
     *
     * @param documents a list of genres to check against
     * @return all movies that match the criteria
     */
    List<Movie> searchGenres(List<String> documents, boolean and);

    /**
     * Searches movies, returning those which have the passed in parameter
     * in any of their titles
     *
     * @param intitle the string to search
     * @return all movies that have the string in any of their titles
     */
    List<Movie> searchTitle(String intitle);
}

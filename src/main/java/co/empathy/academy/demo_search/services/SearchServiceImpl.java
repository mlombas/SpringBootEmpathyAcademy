package co.empathy.academy.demo_search.services;

import co.empathy.academy.demo_search.model.Movie;
import org.json.JSONArray;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SearchServiceImpl implements SearchService {
    private SearchEngine search;

    public SearchServiceImpl(SearchEngine search) {
        this.search = search;
    }

    public String echo(String query) {
	return search.echo(query);
    }

    public String engineVersion() {
	return search.getVersion();
    }

    public Movie postMovie(Movie movie) {
        movie = movie.withId(UUID.randomUUID());
        search.postDocument(movie.toJSON());
        return movie;
    }

    @Override
    public List<Movie> searchGenres(List<String> documents) {
        return searchGenres(documents, true);
    }
    @Override
    public List<Movie> searchGenres(List<String> documents, boolean and) {
        JSONArray res = search.searchGenre(
                documents,
                and
        );

        return jsonArrayToMovieList(res);
    }

    @Override
    public List<Movie> searchTitle(String title) {
        JSONArray res = search.searchTitle(title);
        return jsonArrayToMovieList(res);
    }

    private List<Movie> jsonArrayToMovieList(JSONArray arr) {
        return IntStream.range(0, arr.length()).mapToObj(arr::getJSONObject)
                .map(json -> json.getJSONObject("_source"))
                .map(Movie::make)
                .collect(Collectors.toList());
    }
}

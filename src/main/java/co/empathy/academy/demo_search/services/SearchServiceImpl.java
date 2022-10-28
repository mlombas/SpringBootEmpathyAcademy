package co.empathy.academy.demo_search.services;

import co.empathy.academy.demo_search.model.Movie;
import org.json.JSONArray;

import java.util.List;
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

    public void postMovie(Movie movie) {
        search.postDocument(movie.toJSON());
    }

    @Override
    public List<Movie> searchGenres(String document) {
        return searchGenres(document, true);
    }
    @Override
    public List<Movie> searchGenres(String document, boolean and) {
        JSONArray res = search.searchGenre(
                List.of(document.split(" ")),
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

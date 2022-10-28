package co.empathy.academy.demo_search.services;

import co.empathy.academy.demo_search.model.Movie;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
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

        IntStream.range(0, res.length()).mapToObj(res::getJSONObject)
                .map(json -> json.getJSONObject("_source"))
                .map(Movie::make)
                .collect(Collectors.toList())
                .forEach(System.out::println);

        return IntStream.range(0, res.length()).mapToObj(res::getJSONObject)
                .map(json -> json.getJSONObject("_source"))
                .map(Movie::make)
                .collect(Collectors.toList());
    }
}

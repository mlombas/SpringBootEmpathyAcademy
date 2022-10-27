package co.empathy.academy.demo_search.services;

import co.empathy.academy.demo_search.model.Movie;

import java.util.ArrayList;
import java.util.List;

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
    public void searchGenres(String document) {
        search.searchGenre(
                List.of(document.split(" "))
        );
    }
}

package co.empathy.academy.demo_search.service;

import co.empathy.academy.demo_search.model.Movie;
import co.empathy.academy.demo_search.services.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import org.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class SearchServiceImplTest {

    @Test
    void givenAnyQuery_whenEcho_thenSameIsReturned() {
	String query = "echo";
	SearchEngine searchEngine = mock(ElasticEngine.class);
	given(searchEngine.echo(query)).willReturn(query);

	SearchService searchService = new SearchServiceImpl(searchEngine);

	String echoed = searchService.echo(query);
	assertEquals(query, echoed);
    }

    @Test
    void givenASearchServiceImpl_whenAskedForEngineVersion_thenReturnsSameAsEngine() {
	String version = "1.1.1.1";

	SearchEngine searchEngine = mock(ElasticEngine.class);
	given(searchEngine.getVersion()).willReturn(version);

	SearchService searchService = new SearchServiceImpl(searchEngine);

	String returnedVersion = searchService.engineVersion();

	assertEquals(version, returnedVersion);
    }

    @Test
    void givenAMovie_whenMovieIsPosted_thenMovieIsPosted() {
	SearchEngine searchEngine = mock(ElasticEngine.class);
	SearchService searchService = new SearchServiceImpl(searchEngine);

	searchService.postMovie(new Movie());

	verify(searchEngine, atLeastOnce()).postDocument(any());
    }

    @Test
    void givenAListOfGenres_whenGenresAreSearched_thenGenresWithAndIsCalled() {
	SearchEngine searchEngine = mock(ElasticEngine.class);
	given(searchEngine.searchGenre(any(), anyBoolean()))
	    .willReturn(new JSONArray());
	SearchService searchService = new SearchServiceImpl(searchEngine);

	searchService.searchGenres(
	    List.of(new String[]{"A"})
	);

	verify(searchEngine, atLeastOnce()).searchGenre(any(), eq(true));
    }

    @Test
    void givenAListOfGenres_whenGenresWithOrAreSearched_thenGenresWithOrIsCalled() {
	SearchEngine searchEngine = mock(ElasticEngine.class);
	given(searchEngine.searchGenre(any(), anyBoolean()))
	    .willReturn(new JSONArray());
	SearchService searchService = new SearchServiceImpl(searchEngine);

	searchService.searchGenres(
	    List.of(new String[]{"A"}),
	    false
	);

	verify(searchEngine, atLeastOnce()).searchGenre(any(), eq(false));
    }

    @Test
    void givenATitle_whenSearchTitle_thenTitleIsSearched() {
	SearchEngine searchEngine = mock(ElasticEngine.class);
	given(searchEngine.searchTitle(anyString()))
	    .willReturn(new JSONArray());
	SearchService searchService = new SearchServiceImpl(searchEngine);

	searchEngine.searchTitle("AA");
	
	verify(searchEngine, atLeastOnce()).searchTitle(any());
    }
}

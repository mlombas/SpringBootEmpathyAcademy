package co.empathy.academy.demo_search.service;

import co.empathy.academy.demo_search.services.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@ExtendWith(MockitoExtension.class)
public class ElasticEngineTests {

    @Test
    void givenAnyQuery_whenEcho_thenSameIsReturned() {
	String query = "echo";

	SearchEngine searchEngine = new ElasticEngine(new RestServiceImpl());

	String echoed = searchEngine.echo(query);
	assertEquals(query, echoed);
    }

    @Test
    void givenAElasticEngine_whenAskedForVersion_thenClusterVersionIsReturned() throws JSONException {
	RestService mockedRest = mock(RestService.class);
	String version = "1.1.1.1";
	given(mockedRest.getUrlJSON("http://localhost:9200"))
	    .willReturn(new JSONObject("{'version': {'number': " + version + "}}"));

	SearchEngine searchEngine = new ElasticEngine(mockedRest);

	String returnedVersion = searchEngine.getVersion();
	assertEquals(version, returnedVersion);
    }

    @Test
    void givenAJSONObject_whenPostDocument_thenTheDocumentIsPosted() {
	RestService mockedRest = mock(RestService.class);
	SearchEngine searchEngine = new ElasticEngine(mockedRest);
	searchEngine.postDocument(new JSONObject());

	verify(mockedRest).postJSON(any(), any());
    }

    @Test
    void givenAListOfGenres_whenSearchGenre_thenTheMatchingDocumentsAreReturned() throws JSONException {
	RestService mockedRest = mock(RestService.class);
	Map<String, List<String>> mockDB = new HashMap<>();
	mockDB.put("A", Collections.singletonList("a"));
	mockDB.put("B", Collections.singletonList("b"));
	mockDB.put("AB", List.of(new String[]{"a", "b"}));

	//This whole lot of code is just so when a request is made to postJSON, the corresponding
	//movies are returned
	when(mockedRest.postJSON(any(), any()))
	    .thenAnswer(invocation ->
		    mockHits(
			mockDB.entrySet().stream()
			.filter(entry ->
			    getGenresFromJSON(invocation.getArgument(1, JSONObject.class)).stream()
			    .allMatch(genre -> entry.getValue().contains(genre))
			    )
			.map(Map.Entry::getKey)
			.collect(Collectors.toList())
			)
		    );
	SearchEngine engine = new ElasticEngine(mockedRest);

	var results = engine.searchGenre(List.of(new String[]{"a", "b"}));
	assertEquals(results. getString(0), "AB");
    }

    @Test
    void givenAListOfGenres_whenSearchGenreOr_thenTheMatchingDocumentsAreReturned() throws JSONException {
	RestService mockedRest = mock(RestService.class);
	Map<String, List<String>> mockDB = new HashMap<>();
	mockDB.put("A", Collections.singletonList("a"));
	mockDB.put("B", Collections.singletonList("b"));
	mockDB.put("AB", List.of(new String[]{"a", "b"}));

	//This whole lot of code is just so when a request is made to postJSON, the corresponding
	//movies are returned
	when(mockedRest.postJSON(any(), any()))
	    .thenAnswer(invocation ->
		    mockHits(
			mockDB.entrySet().stream()
			.filter(entry ->
			    getGenresFromJSON(invocation.getArgument(1, JSONObject.class)).stream()
			    .anyMatch(genre -> entry.getValue().contains(genre))
			    )
			.map(Map.Entry::getKey)
			.collect(Collectors.toList())
			)
		    );
	SearchEngine engine = new ElasticEngine(mockedRest);

	var results = engine.searchGenre(List.of(new String[]{"a", "b"}));
	assertEquals(results.length(), 3);
    }

    @Test
    void givenATitle_whenSearchTitle_thenMoviesWithMatchingTitleAreReturned() throws JSONException {
	RestService mockedRest = mock(RestService.class);
	String expected = "TrueTitle";
	String asked = "Title";
	given(
	    mockedRest.postJSON(
		any(),
		argThat(matchJSONStringContains(asked))
	    )
	 )
	.willReturn(mockHits(
	    List.of(new String[]{expected})
	));

	SearchEngine engine = new ElasticEngine(mockedRest);
	var results = engine.searchTitle(asked);
	assertEquals(results.get(0), expected);
    }

    @Test
	@MockitoSettings(strictness = Strictness.LENIENT)
	void givenATitle_whenSearchTitleThatDoesNotExist_thenNoMoviesReturned() throws JSONException {
	RestService mockedRest = mock(RestService.class);
	String expected = "TrueTitle";
	String asked = "title";
	when(
	    mockedRest.postJSON(
		any(),
		any()
	    )
	 )
	.thenAnswer(invocation -> {
		var arg = invocation.getArgument(0, String.class);

		if(arg.equals(asked))
		    return mockHits(
			List.of(new String[]{expected})
		    );
		else return mockHits(new ArrayList<>());
	});

	SearchEngine engine = new ElasticEngine(mockedRest);
	var results = engine.searchTitle("no");
	assertEquals(0, results.length());
    }

    private ArgumentMatcher<JSONObject> matchJSONStringContains(String contains) {
	return  new ArgumentMatcher<JSONObject>() {
	    @Override
	    public boolean matches(JSONObject jsonObject) {
		return jsonObject.toString().contains(contains);
	    }
	};
    }


    private JSONObject mockHits(List<String> hits) throws JSONException {
	return new JSONObject()
	    .put("hits", new JSONObject().put("hits", new JSONArray(hits)));
    }

    private List<String> getGenresFromJSON(JSONObject obj) {
	try {
	    JSONArray arr = obj
		.getJSONObject("query")
		.getJSONObject("bool")
		.getJSONArray("must");
	    return IntStream.range(0, arr.length()).mapToObj(arr::optJSONObject)
		.map(json -> json.optJSONObject("match").optString("genres"))
		.collect(Collectors.toList());
	} catch (Exception e) {
	    return new ArrayList<>();
	}
    }
}

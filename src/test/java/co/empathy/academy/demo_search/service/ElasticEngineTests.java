package co.empathy.academy.demo_search.service;

import co.empathy.academy.demo_search.search.LowLevelElasticEngine;
import co.empathy.academy.demo_search.search.SearchEngine;
import co.empathy.academy.demo_search.services.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ElasticEngineTests {

    @Test
    void givenAnyQuery_whenEcho_thenSameIsReturned() {
	String query = "echo";

	SearchEngine searchEngine = new LowLevelElasticEngine(new RestServiceImpl());

	String echoed = searchEngine.echo(query);
	assertEquals(query, echoed);
    }

    @Test
    void givenAElasticEngine_whenAskedForVersion_thenClusterVersionIsReturned() throws JSONException {
	RestService mockedRest = mock(RestService.class);
	String version = "1.1.1.1";
	given(mockedRest.getUrlJSON("http://localhost:9200"))
	    .willReturn(new JSONObject("{'version': {'number': " + version + "}}"));

	SearchEngine searchEngine = new LowLevelElasticEngine(mockedRest);

	String returnedVersion = searchEngine.getVersion();
	assertEquals(version, returnedVersion);
    }
}

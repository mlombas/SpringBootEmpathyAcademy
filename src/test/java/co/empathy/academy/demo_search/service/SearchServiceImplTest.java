package co.empathy.academy.demo_search.service;

import co.empathy.academy.demo_search.search.LowLevelElasticEngine;
import co.empathy.academy.demo_search.search.SearchEngine;
import co.empathy.academy.demo_search.services.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SearchServiceImplTest {

    @Test
    void givenAnyQuery_whenEcho_thenSameIsReturned() {
	String query = "echo";
	SearchEngine searchEngine = mock(LowLevelElasticEngine.class);
	given(searchEngine.echo(query)).willReturn(query);

	SearchService searchService = new SearchServiceImpl(searchEngine);

	String echoed = searchService.echo(query);
	assertEquals(query, echoed);
    }

    @Test
    void givenASearchServiceImpl_whenAskedForEngineVersion_thenReturnsSameAsEngine() {
	String version = "1.1.1.1";

	SearchEngine searchEngine = mock(LowLevelElasticEngine.class);
	given(searchEngine.getVersion()).willReturn(version);

	SearchService searchService = new SearchServiceImpl(searchEngine);

	String returnedVersion = searchService.engineVersion();

	assertEquals(version, returnedVersion);
    }
}

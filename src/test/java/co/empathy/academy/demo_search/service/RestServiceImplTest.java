package co.empathy.academy.demo_search.service;

import co.empathy.academy.demo_search.services.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RestServiceImplTest {

    @Test
    void givenAnyUrl_whenGetUrl_thenTheAskedForObjectIsReturned() {
	String url = "http://localhost:9200";

	RestService restService = new RestServiceImpl();
	String fetched = restService.getUrl(url, String.class);

	assertTrue(!fetched.isEmpty());
    }

    @Test
    void givenAnyUrl_whenGetUrlJSON_thenJSONObjectIsReturned() throws JSONException {
	String url = "http://localhost:9200";

	RestService restService = new RestServiceImpl();
	JSONObject fetched = restService.getUrlJSON(url);

	assertNotEquals(0, fetched.names().length());
    }
}

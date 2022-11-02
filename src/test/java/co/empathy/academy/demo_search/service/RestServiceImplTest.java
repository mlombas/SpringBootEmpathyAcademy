package co.empathy.academy.demo_search.service;

import co.empathy.academy.demo_search.services.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
public class RestServiceImplTest {
    private static final String URL = "http://localhost:9200";

    @Test
    void givenAnyUrl_whenGetUrl_thenTheAskedForObjectIsReturned() {

	RestService restService = new RestServiceImpl();
	String fetched = restService.getUrl(URL, String.class);

	assertTrue(!fetched.isEmpty());
    }

    @Test
    void givenAnyUrl_whenGetUrlJSON_thenJSONObjectIsReturned() throws JSONException {

	RestService restService = new RestServiceImpl();
	JSONObject fetched = restService.getUrlJSON(URL);

	assertNotEquals(0, fetched.names().length());
    }

    @Test
    void givenAnyUrlAndAnyJson_whenPostJSON_thenJsonObjectIsPosted() throws JSONException {
	JSONObject json = new JSONObject("{'test': 'test'}");

	RestService restService = new RestServiceImpl();
	RestTemplate mock = mock(RestTemplate.class);

	HttpHeaders headers = new HttpHeaders();
	headers.setContentType(MediaType.APPLICATION_JSON);

	given(mock.postForObject(
		    anyString(),
		    eq(new HttpEntity<>(json.toString(), headers)),
		    eq(String.class)
		    )
	     )
	    .willReturn(json.toString());
	ReflectionTestUtils
	    .setField(restService, "template", mock);
	JSONObject fetched = restService.postJSON(URL, json);

	assertNotEquals(0, fetched.names().length());
    }
}

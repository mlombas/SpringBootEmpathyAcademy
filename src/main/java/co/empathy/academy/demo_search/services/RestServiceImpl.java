package co.empathy.academy.demo_search.services;

import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

public class RestServiceImpl implements RestService {
    private RestTemplate template;

    public RestServiceImpl() {
	template = new RestTemplate();
    }

	public <T> T getUrl(String url, Class<T> clazz) {
		return this.template.getForObject(
				url,
				clazz
		);
	}

	public JSONObject getUrlJSON(String url) {
		return new JSONObject(getUrl(url, String.class));
    }

	public JSONObject postJSON(String url, JSONObject json) {
	HttpHeaders headers = new HttpHeaders();
	headers.setContentType(MediaType.APPLICATION_JSON);

	String res = this.template.postForObject(
	    url,
	    new HttpEntity<>(json.toString(), headers),
	    String.class
	);

	return new JSONObject(res);
    }
}

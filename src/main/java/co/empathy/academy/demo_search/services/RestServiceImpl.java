package co.empathy.academy.demo_search.services;

import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

public class RestServiceImpl implements RestService {
    private RestTemplate template;

    public RestServiceImpl() {
	template = new RestTemplate();
    }

    public <T> T getUrl(String url, Class<T> clazz) {
	return this.template.getForObject(url, clazz);
    }

    public JSONObject getUrlJSON(String url) {
	return new JSONObject(getUrl(url, String.class));
    }

    public JSONObject postJSON(String url, JSONObject json) {
	HTTPHeaders headers = new HTTPHEaders();
	headers.setContentType(MediaType.APPLICATION_JSON);
	
	String res = this.template.postForObject(
	    url,
	    new HTTPEntity<String>(json.toString(), headers),
	    String.class
	);

	return new JSONObject(res);
    }
}

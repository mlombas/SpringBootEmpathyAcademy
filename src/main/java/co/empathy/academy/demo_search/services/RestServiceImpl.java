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
}

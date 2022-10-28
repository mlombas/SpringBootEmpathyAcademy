package co.empathy.academy.demo_search.services;

import org.json.JSONObject;

public interface RestService {

    public <T> T getUrl(String url, Class<T> clazz);
    public <T> T getUrl(String url, JSONObject body, Class<T> clazz);

    public JSONObject getUrlJSON(String url);
    public JSONObject getUrlJSON(String url, JSONObject body);

    public JSONObject postJSON(String url, JSONObject json);
}

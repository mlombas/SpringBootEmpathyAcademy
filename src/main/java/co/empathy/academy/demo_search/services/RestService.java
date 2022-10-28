package co.empathy.academy.demo_search.services;

import org.json.JSONObject;

public interface RestService {

    /**
     * GETs an url, tries to return the specified class
     *
     * @param url the url to GET
     * @param clazz the class to return
     * @return the return of the REST call
     * @param <T> the type of the class to return
     */
    public <T> T getUrl(String url, Class<T> clazz);

    /**
     * GETs an url, in JSON format
     *
     * @param url the url to GET
     * @return whatever the REST call returns, in json
     */
    public JSONObject getUrlJSON(String url);

    /**
     * POSTs a JSON object to an url
     *
     * @param url the url to POST to
     * @param json the body of the call
     * @return whatever the REST call returns, in json
     */
    public JSONObject postJSON(String url, JSONObject json);
}

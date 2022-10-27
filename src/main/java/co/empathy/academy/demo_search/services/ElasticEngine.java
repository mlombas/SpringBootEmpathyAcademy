package co.empathy.academy.demo_search.services;

import org.json.JSONObject;

public class ElasticEngine implements SearchEngine, DocumentEngine {
    private static String CLUSTER_URL = "http://localhost:9200";
    private static String TEST_INDEX_NAME = "movies";

    private RestService rest;

    public ElasticEngine(RestService rest) {
	this.rest = rest;
    }

    public String getVersion() {
	JSONObject res =
	    rest.getUrlJSON(CLUSTER_URL);

	return res.getJSONObject("version").getString("number");
    }

    public String echo(String query) {
	return query;
    }

    private String getIndexUrl() {
	return CLUSTER_URL + "/" + TEST_INDEX_NAME;

    public void postDocument(JSONObject obj) {
	rest.postJSON(getIndexUrl() + "/_doc");
    }
}

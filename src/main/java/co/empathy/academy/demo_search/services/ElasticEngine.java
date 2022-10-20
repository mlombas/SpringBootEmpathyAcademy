package co.empathy.academy.demo_search.services;

import org.json.JSONObject;

public class ElasticEngine implements SearchEngine {
    private RestService rest;

    public ElasticEngine(RestService rest) {
	this.rest = rest;
    }

    public String getVersion() {
	JSONObject res =
	    rest.getUrlJSON("http://localhost:9200");

	return res.getJSONObject("version").getString("number");
    }

    public String echo(String query) {
	return query;
    }
}

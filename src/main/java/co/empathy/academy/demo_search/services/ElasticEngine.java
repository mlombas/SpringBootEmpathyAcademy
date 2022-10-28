package co.empathy.academy.demo_search.services;

import co.empathy.academy.demo_search.query.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class ElasticEngine implements SearchEngine {
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
    }

    public void postDocument(JSONObject obj) {
	rest.postJSON(getIndexUrl() + "/_doc", obj);
    }

    @Override
    public JSONArray searchGenre(List<String> genres) {
        return searchGenre(genres, true);
    }
    @Override
    public JSONArray searchGenre(List<String> genres, boolean and) {
        MultipleCompoundQuery insides;
        if(and) insides = new And();
        else insides = new Or();

        for(var genre : genres)
            insides.addCompound(
                    new Match(
                            new LeafQuery("genres", genre)
                    )
            );

        Query query = new MainQuery(new Bool(insides));

        System.out.println(query.make());
        JSONObject response = rest.postJSON(getIndexUrl() + "/_search", query.make());
        return response
                .getJSONObject("hits")
                .getJSONArray("hits");
    }
}

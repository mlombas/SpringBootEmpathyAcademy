package co.empathy.academy.demo_search.services;

public class ElasticEngine implements SearchEngine {
    public String getVersion() {
	return "";
    }

    public String echo(String query) {
	return query;
    }
}

package co.empathy.academy.demo_search.services;

public class SearchServiceImpl implements SearchService {
    private SearchEngine engine;

    public SearchServiceImpl(SearchEngine engine) {
	this.engine = engine;
    }

    public String echo(String query) {
	return engine.echo(query);
    }

    public String engineVersion() {
	return engine.getVersion();
    }
}

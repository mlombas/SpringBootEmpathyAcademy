package co.empathy.academy.demo_search.services;

public interface SearchService {
    public String engineVersion();
    public String echo(String query);

    public String postMovie(Movie m);
}

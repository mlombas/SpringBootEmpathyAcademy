package co.empathy.academy.demo_search.ports.requests.commands.document;

import co.empathy.academy.demo_search.model.Movie;
import co.empathy.academy.demo_search.ports.requests.commands.DocumentCommand;

import java.io.File;
import java.util.List;

public class BulkIndexCommand implements DocumentCommand {
    private File file;
    public BulkIndexCommand(File file) {
        this.file = file;
    }
    
    @Override
    public List<Movie> getMovies() {
        return null;
    }
}

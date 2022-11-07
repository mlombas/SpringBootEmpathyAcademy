package co.empathy.academy.demo_search.ports.requests.commands.document;

import co.empathy.academy.demo_search.model.Movie;
import co.empathy.academy.demo_search.ports.requests.commands.DocumentCommand;
import co.empathy.academy.demo_search.util.TSVReader;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class BulkIndexCommand<T> implements DocumentCommand {
    private File file;
    public BulkIndexCommand(File file) {
        this.file = file;
    }

    @Override
    public List<T> getDocuments() {
        TSVReader<Movie> tsv;
        try {
            tsv = new TSVReader<>(file, Movie.class, "genres");
        } catch (IOException e) {
            tsv = null;
        }

        for(Movie m : tsv)
            System.out.println(m);

        return null;
    }
}

package co.empathy.academy.demo_search.ports.requests.commands.document;

import co.empathy.academy.demo_search.model.Movie;
import co.empathy.academy.demo_search.ports.requests.commands.DocumentCommand;
import co.empathy.academy.demo_search.util.TSVReader;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class BulkIndexCommand<T> implements DocumentCommand {
    private final Class<T> clazz;
    private File file;
    public BulkIndexCommand(File file, Class<T> clazz) {
        this.file = file;
        this.clazz = clazz;
    }

    @Override
    public Iterable<T> getDocuments() {
        TSVReader<T> tsv;
        try {
            tsv = new TSVReader<>(file, clazz, "genres");
        } catch (IOException e) {
            tsv = null;
        }

        return tsv;
    }
}

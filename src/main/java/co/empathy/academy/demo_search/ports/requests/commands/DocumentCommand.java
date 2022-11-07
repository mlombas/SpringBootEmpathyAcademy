package co.empathy.academy.demo_search.ports.requests.commands;

import co.empathy.academy.demo_search.model.Movie;

import java.util.Iterator;
import java.util.List;

public interface DocumentCommand<T> {
    Iterable<T> getDocuments();
}

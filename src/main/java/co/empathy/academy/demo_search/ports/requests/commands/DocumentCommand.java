package co.empathy.academy.demo_search.ports.requests.commands;

public interface DocumentCommand<T> {
    Iterable<T> getDocuments();
}

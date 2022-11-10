package co.empathy.academy.demo_search.ports.requests.commands.document.pipelines;

public interface Pipe<T> {
    T pipe(T base);
}

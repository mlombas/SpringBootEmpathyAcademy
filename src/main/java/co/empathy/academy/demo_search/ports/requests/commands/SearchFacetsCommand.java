package co.empathy.academy.demo_search.ports.requests.commands;

public interface SearchWithAggregationsCommand<T> {
    SearchCommand<T> search;

}

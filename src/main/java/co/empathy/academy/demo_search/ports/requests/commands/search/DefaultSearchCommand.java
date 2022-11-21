package co.empathy.academy.demo_search.ports.requests.commands.search;

import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.empathy.academy.demo_search.ports.filters.PFilterBuilder;
import co.empathy.academy.demo_search.ports.order.POrderBuilder;
import co.empathy.academy.demo_search.ports.queries.PQueryBuilder;
import co.empathy.academy.demo_search.ports.requests.commands.SearchCommand;

import java.util.List;

public abstract class DefaultSearchCommand<T> implements SearchCommand<T> {
    @Override
    public Query buildQuery(PQueryBuilder builder) {
        return builder.all().build();
    }

    @Override
    public Query buildFilter(PFilterBuilder builder) {
        return builder.build();
    }

    @Override
    public List<SortOptions> buildOrder(POrderBuilder builder) { return builder.build(); }

    @Override
    public Integer getMaxNHits() { return 10; }
}

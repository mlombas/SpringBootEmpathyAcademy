package co.empathy.academy.demo_search.ports.requests.commands.search;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.empathy.academy.demo_search.model.Movie;
import co.empathy.academy.demo_search.ports.filters.PFilterBuilder;
import co.empathy.academy.demo_search.ports.queries.PQueryBuilder;
import co.empathy.academy.demo_search.ports.requests.commands.SearchCommand;
import io.micrometer.core.instrument.search.Search;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.With;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class InTitleSearchCommand extends DefaultSearchCommand<Movie> {
    private final String intitle;
    private final SearchFilters filters;
    private CompletableFuture<List<Movie>> future;


    public InTitleSearchCommand(String intitle) {
        this(intitle, new SearchFilters());
    }
    public InTitleSearchCommand(String intitle, SearchFilters filters) {
        this.intitle = intitle;
        this.filters = filters;
        this.future = new CompletableFuture<>();
    }

    @Override
    public Query buildQuery(PQueryBuilder builder) {
        return builder.multi(
                intitle,
                "primaryTitle", "originalTitle"
        ).build();
    }

    @Override
    public Query buildFilter(PFilterBuilder builder) {
        if(filters.getGenre() != null)
            builder.match("genres", filters.getGenre());

        builder.range(
                "runtimeMinutes",
                filters.getDurationMin() == null ?
                        0 : filters.getDurationMin(),
                filters.getDurationMax() == null ?
                        Integer.MAX_VALUE : filters.getDurationMax()
        );

        builder.range(
                "startYear",
                filters.getYearMin() == null ?
                        0 : filters.getYearMin(),
                filters.getYearMax() == null ?
                        Integer.MAX_VALUE : filters.getYearMax()
        );

        return builder.build();
    }

    @Override
    public CompletableFuture<List<Movie>> getFuture() {
        return future;
    }

    @Override
    public void accept(List<Movie> returns) {
        future.complete(returns);
    }

    @Override
    public Class<Movie> getInnerClass() {
        return Movie.class;
    }
}

package co.empathy.academy.demo_search.ports.requests.commands.search;

import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.empathy.academy.demo_search.model.Title;
import co.empathy.academy.demo_search.ports.filters.PFilterBuilder;
import co.empathy.academy.demo_search.ports.order.POrderBuilder;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
public class AllSearchCommand extends DefaultSearchCommand<Title> {
    private CompletableFuture<List<Title>> future
            = new CompletableFuture<>();

    private final Optional<List<String>> genres;
    private final Optional<Integer> minYear;
    private final Optional<Integer> maxYear;
    private final Optional<Integer> minMinutes;
    private final Optional<Integer> maxMinutes;
    private final Optional<Float> minScore;
    private final Optional<Float> maxScore;
    private final Optional<String> type;

    private final Optional<POrderBuilder.Order> sortRating;

    private final Integer maxNHits;

    @Override
    public Query buildFilter(PFilterBuilder builder) {
        if(genres.isPresent()) for(var genre : genres.get())
            builder = builder.match("genres", genre);

        if(type.isPresent())
            builder = builder.match("titleType", type.get());

        return builder
                .range(
                        "startYear",
                        minYear.orElse(0),
                        maxYear.orElse(Integer.MAX_VALUE)
                )
                .range(
                        "runtimeMinutes",
                        minMinutes.orElse(0),
                        maxMinutes.orElse(Integer.MAX_VALUE)
                )
                .range(
                        "averageRating",
                        minScore.orElse(0f),
                        maxScore.orElse(Float.MAX_VALUE)
                )
                .build();
    }

    @Override
    public List<SortOptions> buildOrder(POrderBuilder builder) {
        if(sortRating.isPresent())
            builder = builder.byField("averageRating", sortRating.get());
        return builder.build();
    }

    @Override
    public CompletableFuture<List<Title>> getFuture() {
        return future;
    }

    @Override
    public void accept(List<Title> returns) {
        this.future.complete(returns);
    }

    @Override
    public Integer getMaxNHits() { return maxNHits; }

    @Override
    public Class<Title> getInnerClass() {
        return Title.class;
    }
}

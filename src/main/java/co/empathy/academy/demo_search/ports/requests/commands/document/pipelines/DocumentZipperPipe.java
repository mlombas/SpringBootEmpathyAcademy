package co.empathy.academy.demo_search.ports.requests.commands.document.pipelines;

import java.util.Iterator;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;

public class DocumentZipperPipe<T, TZ> implements Pipe<T> {
    private final BiFunction<T,TZ,T> zipper;
    private final BiPredicate<T,TZ> predicate;
    private final Iterator<TZ> iterator;

    private TZ current;

    public DocumentZipperPipe(
            BiFunction<T, TZ, T> zipper,
            BiPredicate<T, TZ> predicate,
            Iterable<TZ> iterable
    ) {
        this.zipper = zipper;
        this.predicate = predicate;
        this.iterator = iterable.iterator();
        this.current = iterator.next();
    }

    @Override
    public T pipe(T base) {
        if(!predicate.test(base, current)) return base;

        T result = zipper.apply(base, current);
        //Due to how this is constructed, this will:
        // - Do nothing if iterator does not has next
        // - Advance one and stop if iterator has next, but it is not compatible with base
        // - Advance and merge with result otherwise
        while(
                iterator.hasNext() &&
                predicate.test(base, current = iterator.next())
        ) {
            result = zipper.apply(result, current);
        }

        return result;
    }
}

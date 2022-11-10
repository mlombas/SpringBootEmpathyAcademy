package co.empathy.academy.demo_search.ports.requests.commands.document.pipelines;

import co.empathy.academy.demo_search.ports.requests.commands.DocumentCommand;
import co.empathy.academy.demo_search.util.TSVReader;

import java.io.File;
import java.io.IOException;
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
        while(
                iterator.hasNext() &&
                !predicate.test(base, current)
        ) {
            current = iterator.next();
        }

        if(!predicate.test(base, current)) return base;

        while(
                predicate.test(base, current) &&
                iterator.hasNext()
        ) {
            base = zipper.apply(base, current);
            current = iterator.next();
        }

        if(predicate.test(base, current))
            return zipper.apply(base, current);
        return base;
    }
}

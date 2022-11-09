package co.empathy.academy.demo_search.util;

import org.elasticsearch.search.aggregations.metrics.InternalHDRPercentiles;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.function.Supplier;

public class IterableCounter<T> implements Iterable<T> {

    private final Supplier<T> generator;
    private final int max;
    private int count = 0;

    public IterableCounter(Supplier<T> generator, int max) {
        this.generator = generator;
        this.max = max;
    }


    private void tick() {
        count++;
    }

    public int getCount() {
        return count;
    }

    @Override
    public Iterator<T> iterator() {
        return new Counter(this);
    }

    private class Counter implements Iterator<T> {
        private final IterableCounter iterable;

        public Counter(IterableCounter iterable) {
            this.iterable = iterable;
        }

        @Override
        public boolean hasNext() {
            return iterable.getCount() < max;
        }

        @Override
        public T next() {
            iterable.tick();

            return (T) iterable.generator.get();
        }
    }

}

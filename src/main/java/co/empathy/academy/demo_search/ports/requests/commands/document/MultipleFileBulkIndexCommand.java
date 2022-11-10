package co.empathy.academy.demo_search.ports.requests.commands.document;

import co.empathy.academy.demo_search.ports.requests.commands.DocumentCommand;
import co.empathy.academy.demo_search.util.TSVReader;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

//TODO need to do this more generic
//I have idea: take 1 file as principal, and others as "appendages"
public class MultipleFileBulkIndexCommand<T, T1> implements DocumentCommand<T> {

    private final BiFunction<T,T1,T> zipper;
    private final BiPredicate<T, T1> predicate;

    private final Class<T> clazz;
    private final File file2;
    private final File file1;
    private final Class<T1> clazz1;

    public MultipleFileBulkIndexCommand(
            BiFunction<T, T1, T> zipper,
            BiPredicate<T, T1> predicate,
            File file1, File file2,
            Class<T> clazz, Class<T1> clazz1
    ) {
        this.zipper = zipper;
        this.predicate = predicate;

        this.file1 = file1;
        this.file2 = file2;

        this.clazz = clazz;
        this.clazz1 = clazz1;
    }

    @Override
    public Iterable<T> getDocuments() {
        TSVReader<T> tsv;
        TSVReader<T1> tsv1;
        try {
            tsv = new TSVReader<>(file1, clazz, "genres");
            tsv1 = new TSVReader<>(file2, clazz1);
        } catch (IOException e) {
            tsv = null;
            tsv1 = null;
        }

        Iterator<T> iter = tsv.iterator();
        Iterator<T1> iter1 = tsv1.iterator();

        //Uglyyyyy
        return new Iterable<T>() {
            @Override
            public Iterator<T> iterator() {
                return new Iterator<T>() {
                    @Override
                    public boolean hasNext() {
                        return iter.hasNext();
                    }

                    @Override
                    public T next() {
                        T currentT = iter.next();
                        T1 currentT1;

                        if(iter1.hasNext())
                            do currentT1 = iter1.next();
                            while (
                                    iter1.hasNext() &&
                                    !predicate.test(currentT, currentT1)
                            );
                        else return currentT;

                        return zipper.apply(currentT, currentT1);
                    }
                };
            }
        };
    }
}

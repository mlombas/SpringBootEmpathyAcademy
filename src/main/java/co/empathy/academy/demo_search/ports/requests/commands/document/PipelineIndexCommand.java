package co.empathy.academy.demo_search.ports.requests.commands.document;

import co.empathy.academy.demo_search.ports.requests.commands.DocumentCommand;
import co.empathy.academy.demo_search.ports.requests.commands.document.pipelines.Pipe;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class PipelineIndexCommand<T> implements DocumentCommand<T> {
    private final Iterable<T> base;
    private List<Pipe<T>> pipeline;

    public PipelineIndexCommand(Iterable<T> base) {
        this.base = base;
        this.pipeline = new LinkedList<>();
    }

    public void addPipe(Pipe<T> pipe) {
        pipeline.add(pipe);
    }

    @Override
    public Iterable<T> getDocuments() {
        return new Iterable<T>() {
            @Override
            public Iterator<T> iterator() {
                var iterator = base.iterator();
                return new Iterator<T>() {
                    @Override
                    public boolean hasNext() {
                        return iterator.hasNext();
                    }

                    @Override
                    public T next() {
                        T next = iterator.next();
                        for(var pipe : pipeline)
                            next = pipe.pipe(next);
                        return next;
                    }
                };
            }
        };
    }
}

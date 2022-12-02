package co.empathy.academy.demo_search.ports.async;


import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.function.Supplier;

public interface PAsyncMaker {
    public enum Status {
        UNSTARTED,
        RUNNING,
        BLOCKED,
        FINISHED
    }

    default UUID addJob(Runnable run) {
        return addJob(() -> { run.run(); return null; });
    }
    UUID addJob(Supplier<Object> sup);

    Status checkStatus(UUID uuid) throws NoSuchElementException;

    Object getResult(UUID uuid);
}

package co.empathy.academy.demo_search.ports.async.adapters;

import co.empathy.academy.demo_search.ports.async.PAsyncMaker;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.function.Supplier;

public class CustomAsyncMaker implements PAsyncMaker {
    private class Runner extends Thread {
        private Supplier<Object> func;
        @Getter
        private volatile Status status;
        private volatile Object result;

        private Runner(Supplier<Object> func) {
            this.func = func;

            this.status = Status.UNSTARTED;
        }

        @Override
        public void run() {
            this.status = Status.RUNNING;

            try {
                this.result = this.func.get();
            } catch (Exception e) {
                this.status = Status.BLOCKED;
                return;
            }

            this.status = Status.FINISHED;
        }

        public Object getResult() {
            if(this.status != Status.FINISHED)
                throw new RuntimeException("Job not finished");

            return this.result;
        }
    }

    private Map<UUID, Runner> jobs;

    public CustomAsyncMaker() {
        jobs = new HashMap<>();
    }

    @Override
    public UUID addJob(Supplier<Object> sup) {
        UUID id;
        do {
            id = UUID.randomUUID();
        } while (jobs.keySet().contains(id));

        Runner runner = new Runner(sup);
        jobs.put(id, runner);
        runner.start();

        return id;
    }

    @Override
    public Status checkStatus(UUID uuid) throws NoSuchElementException {
        if(!jobs.containsKey(uuid))
            throw new NoSuchElementException("No such key");

        return jobs.get(uuid).getStatus();
    }

    @Override
    public Object getResult(UUID uuid) {
        return jobs.get(uuid).getResult();
    }
}

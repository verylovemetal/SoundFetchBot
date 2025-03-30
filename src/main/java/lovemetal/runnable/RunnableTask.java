package lovemetal.runnable;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RunnableTask {

    private final ScheduledExecutorService scheduler;

    private Runnable runnable;
    private long delay;
    private TimeUnit timeUnit;

    public RunnableTask() {
        this.scheduler = Executors.newScheduledThreadPool(1);
    }

    public RunnableTask setRunnable(Runnable runnable) {
        this.runnable = runnable;
        return this;
    }

    public RunnableTask setDelay(long delay) {
        this.delay = delay;
        return this;
    }

    public RunnableTask setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
        return this;
    }

    public void schedule() {
        scheduler.schedule(runnable, delay, timeUnit);
    }
}

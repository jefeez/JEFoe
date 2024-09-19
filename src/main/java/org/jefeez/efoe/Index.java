package org.jefeez.efoe;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Index {

    private boolean isPaused;

    private static ScheduledExecutorService scheduled;

    public void run() {
       if (Index.scheduled == null || Index.scheduled.isShutdown()) {
           Index.scheduled = Executors.newScheduledThreadPool(1);
           Index.scheduled.scheduleWithFixedDelay(() -> {
               if (this.isPaused) {
                   System.out.println("UNPAUSED");
               } else {
                   System.out.println("PAUSED");
               }
           }, 0, 100, TimeUnit.MILLISECONDS);
       }
    }

    public void shutdown () {
        scheduled.shutdown();
    }

    public ScheduledExecutorService scheduler() {
        return scheduled;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }


}

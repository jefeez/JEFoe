package org.jefeez.efoe;

import org.jefeez.efoe.domain.Battlefield;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Index {

    private boolean isPaused;
    private  String type;

    public void setType(String type) {
        this.type = type;
    }

    private static ScheduledExecutorService scheduled;

    public void run() {
       if (Index.scheduled == null || Index.scheduled.isShutdown()) {
           Battlefield battlefield = new Battlefield();
           Index.scheduled = Executors.newScheduledThreadPool(1);
           Index.scheduled.scheduleWithFixedDelay(() -> {
               if (!this.isPaused) {
                   if (type.equals("CHAIN")) battlefield.chain();
                   if (type.equals("RANDOM")) battlefield.random();
               }
           }, 0, 25, TimeUnit.MILLISECONDS);
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

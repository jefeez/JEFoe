package org.jefeez.efoe;

import javafx.application.Platform;
import javafx.scene.control.Label;
import org.jefeez.efoe.domain.Battlefield;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Index {

    private boolean isPaused;

    private String type = "CHAIN";
    private Label lbBattles;

    public void setType(String type) {
        this.type = type;
    }

    public Label getLbBattles() {
        return lbBattles;
    }

    public void setLbBattles(Label lbBattles) {
        this.lbBattles = lbBattles;
    }


    private static ScheduledExecutorService scheduled;

    public void run() {
        if (Index.scheduled == null || Index.scheduled.isShutdown()) {
            Battlefield battlefield = new Battlefield();
            Index.scheduled = Executors.newScheduledThreadPool(1);
            Index.scheduled.scheduleWithFixedDelay(() -> {
                System.out.println(App.primaryStage.isFocused());
                try {
                    if (!this.isPaused && !App.primaryStage.isFocused()) {
                        if (type.equals("CHAIN")) battlefield.chain();
                        if (type.equals("RANDOM")) battlefield.random();
                        Platform.runLater(() -> {
                            this.getLbBattles().setText(Integer.toString(battlefield.getBattleCount()));
                        });
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                System.gc();
            }, 0, 25, TimeUnit.MILLISECONDS);
        }
    }

    public void shutdown() {
        scheduled.shutdown();
    }

    public ScheduledExecutorService scheduler() {
        return scheduled;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }


}

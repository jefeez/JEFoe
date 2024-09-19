package org.jefeez.efoe;


import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable, NativeKeyListener {

    @FXML
    public Button btnPause;
    @FXML
    public Button btnStart;
    @FXML
    public Label lbStatus;

    private Index index = new Index();

    private boolean isStarted = false;
    private boolean isPaused = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            GlobalScreen.registerNativeHook();
            GlobalScreen.addNativeKeyListener(this);
        } catch (NativeHookException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        Platform.runLater(() -> {
            // onStart
            if (e.getKeyCode() == 31) {
                this.start();
            }

            // onPause
            if (e.getKeyCode() == 25) {
                this.pause();
            }
        });
    }


    @FXML
    public void onStart(ActionEvent event) {
        this.start();
    }

    @FXML
    public void onPause(ActionEvent event) {
        this.pause();
    }

    private void start() {
        this.isStarted = !this.isStarted;
        if (this.isStarted) {
            this.index.run();
            this.lbStatus.setText("RUNNING");
            this.btnStart.setText("STOP");
            this.btnPause.setDisable(false);
            this.isPaused = false;
            this.index.setPaused(false);
        } else {
            this.index.shutdown();
            this.lbStatus.setText("STOPPED");
            this.btnStart.setText("START");
            this.btnPause.setDisable(true);
        }
    }

    private void pause() {
        this.isPaused = !this.isPaused;
        this.index.setPaused(this.isPaused);
        if (this.isPaused) {
            this.lbStatus.setText("PAUSED");
            this.btnPause.setText("UNPAUSED");
            //this.btnStart.setDisable(true);
        } else {
            this.lbStatus.setText("RUNNING");
            this.btnPause.setText("PAUSE");
            this.btnStart.setDisable(false);
        }
    }


}
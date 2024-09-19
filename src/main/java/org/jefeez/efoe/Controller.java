package org.jefeez.efoe;


import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable, NativeKeyListener {

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
        } else {
            this.index.shutdown();
        }
    }

    private void pause() {
        this.isPaused = !this.isPaused;
        this.index.setPaused(this.isPaused);
    }


}
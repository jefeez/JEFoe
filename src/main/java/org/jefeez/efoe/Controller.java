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
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable, NativeKeyListener {

    @FXML
    public Button btnPause;
    @FXML
    public Button btnStart;
    @FXML
    public Label lbStatus;
    @FXML
    public ToggleGroup type;
    public RadioButton rbChain;
    public RadioButton rbRandom;

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
            this.rbChain.setDisable(true);
            this.rbRandom.setDisable(true);
            RadioButton selected = (RadioButton)this.type.getSelectedToggle();
            this.index.setType(selected.getText());

        } else {
            this.index.shutdown();
            this.lbStatus.setText("STOPPED");
            this.btnStart.setText("START");
            this.btnPause.setDisable(true);
            this.rbChain.setDisable(false);
            this.rbRandom.setDisable(false);
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


    public void onChain(ActionEvent event) {
    }

    public void onRandom(ActionEvent event) {
    }
}
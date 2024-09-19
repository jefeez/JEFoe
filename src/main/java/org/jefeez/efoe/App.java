package org.jefeez.efoe;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.opencv.core.Core;

import java.io.IOException;



public class App extends Application {

    private final Index index = new Index();

    @Override
    public void start(Stage stage) throws IOException {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("EFoe");
        stage.setAlwaysOnTop(true);
        stage.setResizable(false);
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX(screenBounds.getMaxX() - stage.getWidth());
        stage.setY(screenBounds.getMinX());
        stage.setScene(scene);
        stage.show();
    }

    public void run () {
        launch();
    }

    @Override
    public void stop() throws NativeHookException {
        GlobalScreen.unregisterNativeHook();
        if (index.scheduler() != null && !index.scheduler().isShutdown()) {
            index.scheduler().shutdown();
        }
    }

}
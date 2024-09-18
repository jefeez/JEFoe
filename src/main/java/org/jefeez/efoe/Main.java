package org.jefeez.efoe;

import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

public class Main implements NativeKeyListener {
    public static void main(String[] args) {
        App app = new App();
        app.run();
    }
}

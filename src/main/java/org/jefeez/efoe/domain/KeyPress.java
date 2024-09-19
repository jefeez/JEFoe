package org.jefeez.efoe.domain;
import java.awt.*;

public class KeyPress {
    public static void press(int key) {
        try {
            Robot robot = new Robot();
            robot.keyPress(key);
            Thread.sleep(100);
            robot.keyRelease(key);
        } catch (AWTException | InterruptedException e) {
            System.out.println(e.getMessage());
        }

    }
}
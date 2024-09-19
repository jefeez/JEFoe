package org.jefeez.efoe.domain;

import java.io.File;
import java.io.IOException;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Screen {
    public static BufferedImage screenshot() {
        try {
            Robot robot = new Robot();
            Rectangle screenSize = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            return robot.createScreenCapture(screenSize);
        } catch (AWTException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static void write(BufferedImage image, String filename) {
        try {
            ImageIO.write(image, "PNG", new File(filename));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
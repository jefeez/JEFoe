package org.jefeez.efoe.domain;

import org.opencv.core.Mat;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Battlefield {

    private Mat png_attack = OpenCv.read("./images/attack.png");
    private Mat png_replace_units = OpenCv.read("./images/replace_units.png");
    private Mat png_auto_battle = OpenCv.read("./images/auto_battle.png");
    private Mat png_start_next_battle = OpenCv.read("./images/start_next_battle.png");
    private Mat png_result_of_battle = OpenCv.read("./images/result_of_battle.png");
    private Mat png_spoils_of_war = OpenCv.read("./images/spoils_of_war.png");

    private State attack;
    private State replace;
    private State battle;
    private State next;
    private State result;
    private State reward;

    public Battlefield() {
        this.attack = new State(this.png_attack, KeyEvent.VK_A, 0.9f);
        this.replace = new State(this.png_replace_units, KeyEvent.VK_R, 0.9f, 300);
        this.battle = new State(this.png_auto_battle, KeyEvent.VK_B, 0.9f);
        this.next = new State(this.png_start_next_battle, KeyEvent.VK_B, 0.9f);
        this.result = new State(png_result_of_battle, KeyEvent.VK_ESCAPE, 0.9f);
        this.reward = new State(png_spoils_of_war, KeyEvent.VK_ESCAPE, 0.9f);
    }

    private Mat screenshot() {
        BufferedImage image = Screen.screenshot();
        if (image == null) return new Mat();
        return OpenCv.matify(image);
    }

    public void chain() {
        Mat screenshot = this.screenshot();
        if (attack.run(screenshot)) attack.resetables(result, reward);
        replace.run(screenshot, attack);
        battle.run(screenshot, replace);
        next.run(screenshot, battle);
        if (result.run(screenshot, battle)) result.resetables(attack, replace, battle, next);
        reward.run(screenshot, result);
        System.gc();
    }

    public void random() {
        Mat screenshot = this.screenshot();
        if (attack.run(screenshot)) attack.resetables(result, reward);
        replace.run(screenshot);
        battle.run(screenshot);
        next.run(screenshot);
        if (result.run(screenshot)) result.resetables(attack, replace, battle, next);
        reward.run(screenshot);
        System.gc();
    }


}

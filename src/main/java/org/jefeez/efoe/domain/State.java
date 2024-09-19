package org.jefeez.efoe.domain;

import org.opencv.core.Mat;

public class State {
    private boolean isFlag;
    private final Mat template;
    public final OpenCv openCv;
    private final double threshold;
    private final int keycode;
    private long sleep = 0;
    private int count;

    public State(Mat template, int keycode, double threshold) {
        this.openCv = new OpenCv();
        this.template = template;
        this.threshold = threshold;
        this.keycode = keycode;

    }

    public State(Mat template, int keycode, double threshold, long sleep) {
        this.openCv = new OpenCv();
        this.template = template;
        this.threshold = threshold;
        this.keycode = keycode;
        this.sleep = sleep;
    }

    public boolean run(Mat screenshot, State state) {
        if (!this.isFlag && state.getIsFlag()) {
            if (this.openCv.has(screenshot, this.template, this.threshold)) {
                this.isFlag = true;
                KeyPress.press(this.keycode);
                this.count++;
                try {
                    Thread.sleep(this.sleep);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                return true;
            }
        }
        return false;
    }

    public boolean run(Mat screenshot) {
        if (!this.isFlag) {
            if (this.openCv.has(screenshot, this.template, this.threshold)) {
                this.isFlag = true;
                KeyPress.press(this.keycode);
                this.count++;
                try {
                    Thread.sleep(this.sleep);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                return true;
            }
        }
        return false;
    }

    public boolean getIsFlag() {
        return this.isFlag;
    }

    public void reset() {
        if (this.isFlag) {
            this.isFlag = false;
        }
    }

    public void resetables(State... states) {
        for (State state : states) {
            state.reset();
        }
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

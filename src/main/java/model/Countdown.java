package model;

import java.util.Date;

public class Countdown {
    private int second;
    private Date date;
    private long current_time = 0;

    public Countdown(int second) {
        this.second = second;
        date = new Date();
    }

    public void start() {
        current_time = date.getTime();
    }

    public long timeRemaining() {
        date = new Date();

        if (current_time == 0) {
            return 0;
        }

        if (second - ((date.getTime() - current_time) * 0.001) < 0) {
            return 0;
        }

        return (long) (second - (date.getTime() - current_time) * 0.001);
    }

    public void reset() {
        current_time = 0;
    }
}

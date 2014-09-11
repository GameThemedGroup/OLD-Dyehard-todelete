package dyehard.Util;

import dyehard.DyeHard;
import dyehard.UpdateObject;

public class Timer extends UpdateObject {
    private float currentTime;
    private float endTime;
    private float interval;

    public Timer(float milliSeconds) {
        // Converting milliseconds to nanoseconds
        currentTime = 0;
        interval = milliSeconds;
        endTime = currentTime + interval;
    }

    public boolean isDone() {
        return currentTime >= endTime;
    }

    public void reset() {
        endTime = currentTime + interval;
    }

    public void setInterval(float milliSeconds) {
        interval = milliSeconds;
    }

    public float timeRemaining() {
        // Returns the amount of time left in milliseconds
        return endTime - currentTime;
    }

    @Override
    public void update() {
        currentTime += DyeHard.DELTA_TIME * 1000;
    }
}

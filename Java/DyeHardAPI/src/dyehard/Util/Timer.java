package dyehard.Util;

public class Timer {
    private final float startTime;
    private float endTime;
    private float interval;

    public Timer(float milliSeconds) {
        startTime = System.nanoTime();
        interval = milliSeconds * 1000000; // milli to nano
        endTime = startTime + interval;
    }

    public boolean isDone() {
        return System.nanoTime() >= endTime;
    }

    public void reset() {
        endTime = System.nanoTime() + interval;
    }

    public void setInterval(float milliSeconds) {
        interval = milliSeconds * 1000000; // milli to nano
    }

    public float timeRemaining() {
        // Returns the amount of time left in milliseconds
        return (endTime - System.nanoTime()) / 1000000;
    }

}

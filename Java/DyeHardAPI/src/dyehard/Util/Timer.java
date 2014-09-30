package dyehard.Util;

public class Timer {
    private float startTime;
    private float endTime;
    private float interval;

    public Timer(float milliSeconds) {
        startTime = System.nanoTime();
        interval = milliSeconds * 1000000; // milli to nano
        endTime = startTime + interval;
    }

    public Timer() {
        startTime = System.nanoTime();
        interval = 100000000;
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

    public float deltaTime() {
        float delta = (System.nanoTime() - startTime) / 1000000000;
        startTime = System.nanoTime();
        return delta;
    }

}

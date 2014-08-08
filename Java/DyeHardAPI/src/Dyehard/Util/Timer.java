package dyehard.Util;

public class Timer {
    // All times are in nanoseconds
    private float endTime;
    private float interval;

    public Timer(float milliSeconds) {
        // Converting milliseconds to nanoseconds
        interval = milliSeconds * 1000000;
        endTime = System.nanoTime() + interval;
    }

    public boolean isDone() {
        return System.nanoTime() >= endTime;
    }

    public void reset() {
        endTime = System.nanoTime() + interval;
    }

    public void stop() {
        endTime = 0f;
    }

    public float timeRemaining() {
        // Returns the amount of time left in milliseconds
        float timeLeft = (endTime - System.nanoTime()) / 1000000;
        if (timeLeft >= 0f) {
            return timeLeft;
        } else {
            return 0;
        }
    }
}

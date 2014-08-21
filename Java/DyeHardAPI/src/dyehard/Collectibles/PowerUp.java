package dyehard.Collectibles;

import java.awt.Color;

import Engine.Text;
import dyehard.Player.Hero;
import dyehard.Util.Timer;

public abstract class PowerUp extends Collectible implements
        Comparable<PowerUp> {

    public static final float DURATION = 5000f;
    private final float height = 2f;
    private final float width = 5f;
    protected Hero hero;
    protected Text label;

    protected Timer timer;
    protected int usageOrder;

    public PowerUp(Hero hero) {
        this.hero = hero;
        size.set(width, height);
        shouldTravel = true;
        label = new Text("", center.getX(), center.getY());
        label.setFrontColor(Color.black);
        label.setBackColor(Color.black);
        label.setFontSize(20);
        label.setFontName("Arial");
    }

    @Override
    public void update() {
        super.update();
        label.center.set(center.getX() - 2.5f, center.getY() - 0.5f);
        if (collided(hero) && visible) {
            hero.collect(this);
        }
    }

    @Override
    public int compareTo(PowerUp other) {
        return usageOrder - other.usageOrder;
    }

    @Override
    public String toString() {
        return String.format("%.0f", (timer.timeRemaining() + 1) / 1000);
    }

    public boolean isDone() {
        return timer.isDone();
    }

    public float getRemainingTime() {
        return Math.max(0f, timer.timeRemaining());
    }

    public abstract void apply();

    public abstract void unapply();

    @Override
    public void destroy() {
        label.destroy();
        super.destroy();
    }

    @Override
    public void activate() {
        timer = new Timer(DURATION);
        destroy();
    }

}

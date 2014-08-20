package dyehard.Collectibles;

import java.awt.Color;
import java.util.List;
import java.util.Random;

import Engine.Text;
import Engine.Vector2;
import dyehard.Enemies.Enemy;
import dyehard.Player.Hero;
import dyehard.Util.Timer;
import dyehard.World.GameWorld;

public abstract class PowerUp extends Collectible implements
        Comparable<PowerUp> {
    private static Random RANDOM = new Random();
    public static final float DURATION = 5000f;
    private final float height = 2f;
    private final float width = 5f;
    protected Hero hero;
    protected Text label;

    protected Timer timer;
    protected int usageOrder;

    public PowerUp(Hero hero, float minX, float maxX) {
        this.hero = hero;
        float randomX = (maxX - minX - width) * RANDOM.nextFloat() + minX
                + width / 2f;
        float randomY = (GameWorld.TOP_EDGE - GameWorld.BOTTOM_EDGE - height)
                * RANDOM.nextFloat() + height / 2f;
        center.set(new Vector2(randomX, randomY));
        size.set(width, height);
        velocity = new Vector2(-GameWorld.Speed, 0f);
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

    public static PowerUp randomPowerUp(Hero hero, List<Enemy> enemies,
            float minX, float maxX) {
        Random rand = new Random();
        switch (rand.nextInt(8)) {
        case 0:
            return new SpeedUp(hero, enemies, minX, maxX);
        case 1:
            return new SlowDown(hero, enemies, minX, maxX);
        case 2:
            return new Ghost(hero, minX, maxX);
        case 3:
            return new Invincibility(hero, minX, maxX);
        case 4:
            return new Unarmed(hero, minX, maxX);
        case 5:
            return new Magnetism(hero, minX, maxX);
        case 6:
            return new Gravity(hero, minX, maxX);
        default:
            return new Overload(hero, minX, maxX);
        }
    }
}

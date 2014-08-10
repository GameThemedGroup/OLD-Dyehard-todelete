package dyehard.Collectibles;

import Engine.Vector2;
import dyehard.Collidable;
import dyehard.Player.Hero;

public abstract class PowerUp extends Collidable implements Cloneable,
        Comparable<PowerUp> {
    public final float Duration = 5f;
    public static final float height = 2f;
    public static float width = 5f;
    protected int applicationOrder;

    public PowerUp() {
        shouldTravel = false;
        visible = false;
        applicationOrder = 0;
    }

    public PowerUp(PowerUp other) {
        texture = other.texture;
    }

    public void initialize(Vector2 center, Vector2 velocity) {
        this.center = center;
        this.velocity = velocity;

        size.set(width, height);
        shouldTravel = true;
        visible = true;
    }

    public abstract void apply(Hero hero);

    public abstract void unapply(Hero hero);

    public void activate(Hero hero) {
        System.out.println("Activating " + toString());
        destroy();
    }

    @Override
    public void handleCollision(Collidable other) {
        if (other instanceof Hero) {
            Hero hero = (Hero) other;
            hero.collect(this);
        }
    }

    @Override
    public abstract PowerUp clone();

    @Override
    public int compareTo(PowerUp other) {
        return applicationOrder - other.applicationOrder;
    }
}

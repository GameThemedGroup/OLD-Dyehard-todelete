package dyehard.Collectibles;

import Engine.Rectangle;
import Engine.Vector2;
import dyehard.Player.Hero;

public class PowerUp extends Rectangle {
    public final float Duration = 5f;
    public static final float height = 2f;
    public static float width = 5f;

    protected Hero hero;

    public PowerUp(Hero hero) {
        this.hero = hero;
        shouldTravel = false;
        visible = false;
    }

    public PowerUp(PowerUp other) {
        super(other);
        hero = other.hero;
    }

    public void initialize(Vector2 center, Vector2 velocity) {
        this.center = center;
        this.velocity = velocity;

        size.set(width, height);
        shouldTravel = true;
        visible = true;
    }

    @Override
    public void update() {
        super.update();
        if (collided(hero) && visible) {
            hero.collect(this);
        }
    }

    public void activate() {
        destroy();
    }
}

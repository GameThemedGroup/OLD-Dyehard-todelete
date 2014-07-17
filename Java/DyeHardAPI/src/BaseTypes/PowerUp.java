package BaseTypes;

import java.util.Random;

import Dyehard.Player.Hero;
import Dyehard.Powerups.Ghost;
import Dyehard.Powerups.Invincibility;
import Dyehard.Powerups.Overload;
import Dyehard.Powerups.SpeedUp;
import Engine.Rectangle;
import Engine.Vector2;

public class PowerUp extends Rectangle {
    public final float Duration = 5f;
    private final float width = 5f;
    protected Hero hero;

    public PowerUp(Hero hero, float minX, float maxX) {
        this.hero = hero;
        float padding = hero.size.getX() * 2;
        Random rand = new Random();
        float randomX = (maxX - padding - minX + padding) * rand.nextFloat()
                + minX + padding;
        // TODO: 50f and 0f are place holders for topEdge and bottomEdge
        float randomY = (50f - padding - 0f + padding) * rand.nextFloat() + 0f
                + padding;
        center.set(new Vector2(randomX, randomY));
        size.set(width, width * 0.39f);
    }

    @Override
    public void update() {
        if (collided(hero) && visible) {
            hero.collect(this);
        }
    }

    public void activate() {
        destroy();
    }

    public static PowerUp randomPowerUp(Hero hero, float minX, float maxX) {
        Random rand = new Random();
        switch (rand.nextInt(4)) {
        case 0:
            return new SpeedUp(hero, minX, maxX);
        case 1:
            return new Ghost(hero, minX, maxX);
        case 2:
            return new Invincibility(hero, minX, maxX);
        default:
            return new Overload(hero, minX, maxX);
        }
    }
}

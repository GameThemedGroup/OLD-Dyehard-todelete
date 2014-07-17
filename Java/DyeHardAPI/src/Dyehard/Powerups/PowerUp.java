package Dyehard.Powerups;

import java.util.Random;

import Dyehard.GameObject;
import Dyehard.Player.Hero;
import Dyehard.World.GameWorld;
import Engine.Rectangle;
import Engine.Vector2;

public class PowerUp extends GameObject {
    public final float Duration = 5f;
    private final float width = 5f;
    protected Hero hero;
    protected Rectangle box;

    public PowerUp(Hero hero, float minX, float maxX) {
        this.hero = hero;
        float padding = hero.getPosition().size.getX() * 2;
        Random rand = new Random();
        float randomX = (maxX - padding - minX + padding) * rand.nextFloat()
                + minX + padding;
        // TODO: 480f and 0f are place holders for topEdge and bottomEdge
        float randomY = (480f - padding - 0f + padding) * rand.nextFloat() + 0f
                + padding;
        box.center.set(new Vector2(randomX, randomY));
        box.size.set(width, width * 0.39f);
    }

    @Override
    public void remove() {
        box.removeFromAutoDrawSet();
    }

    public void move() {
        box.center.setX(box.center.getX() - GameWorld.Speed);
    }

    @Override
    public void update() {
        if (box.collided(hero.getPosition()) && box.visible) {
            hero.collect(this);
        }
    }

    @Override
    public void draw() {
        box.removeFromAutoDrawSet();
        box.addToAutoDrawSet();
    }

    public void activate() {
        box.visible = false;
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

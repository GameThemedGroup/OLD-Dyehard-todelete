package Dyehard.Weapons;

import java.util.LinkedList;
import java.util.Queue;

import Dyehard.GameObject;
import Dyehard.Player.Hero;
import Engine.Rectangle;

public class Weapon extends GameObject {
    protected static float bulletSpeed = 1f;
    protected static float bulletSize = .75f;
    protected Hero hero;
    protected Queue<Rectangle> bullets;

    public Weapon(Hero hero) {
        this.hero = hero;
        bullets = new LinkedList<Rectangle>();
    }

    @Override
    public void update() {
        for (Rectangle b : bullets) {
            b.center.setX(b.center.getX() + bulletSpeed);
        }
        // TODO: 480 is a temporary place holder for the rightEdge variable
        while (bullets.size() > 0
                && (bullets.peek().center.getX() - bullets.peek().size.getX()) > 480) {
            bullets.remove().removeFromAutoDrawSet();
        }
    }

    // Fire the weapon
    public void fire() {
        Rectangle bullet = new Rectangle();
        bullet.center.set(hero.getPosition().center);
        bullet.size.set(bulletSize, bulletSize);
        bullet.color = hero.getColor();
        bullets.add(bullet);
    }

    // Draw the bullets
    @Override
    public void draw() {
        for (Rectangle b : bullets) {
            b.removeFromAutoDrawSet();
            b.addToAutoDrawSet();
        }
    }

    @Override
    public void remove() {
        for (Rectangle b : bullets) {
            b.removeFromAutoDrawSet();
        }
    }
}

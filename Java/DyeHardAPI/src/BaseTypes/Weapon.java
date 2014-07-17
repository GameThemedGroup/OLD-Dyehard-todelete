package BaseTypes;

import java.util.LinkedList;
import java.util.Queue;

import Dyehard.Player.Hero;
import Engine.Rectangle;

public class Weapon extends Rectangle {
    protected static float bulletSpeed = 1f;
    // Was 0.75f, but it was a radius and we are not
    // working with circles
    protected static float bulletSize = 1.5f;
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
        // TODO: 800 is a temporary place holder for the rightEdge variable
        while (bullets.size() > 0
                && (bullets.peek().center.getX() - bullets.peek().size.getX()) > 800) {
            bullets.remove().removeFromAutoDrawSet();
        }
    }

    // Fire the weapon
    public void fire() {
        Rectangle bullet = new Rectangle();
        bullet.center.set(hero.center);
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
    public void destroy() {
        for (Rectangle b : bullets) {
            b.removeFromAutoDrawSet();
        }
        super.destroy();
    }
}

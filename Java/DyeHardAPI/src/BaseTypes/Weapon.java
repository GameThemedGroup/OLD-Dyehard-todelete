package BaseTypes;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import Dyehard.Player.Hero;
import Dyehard.World.GameWorld;
import Engine.Rectangle;

public class Weapon extends Rectangle {
    protected static float bulletSpeed = 1f;
    // Was 0.75f, but it was a radius and we are not
    // working with circles
    protected static float bulletSize = 1.5f;
    protected Hero hero;
    protected Queue<Rectangle> bullets;
    protected ArrayList<Enemy> enemies;
    protected int lastTick;
    protected int currentTick;
    // Weapon fires 4 bullets/second
    protected final int fireRate = 10;

    public Weapon(Hero hero) {
        this.hero = hero;
        bullets = new LinkedList<Rectangle>();
        lastTick = 0;
        currentTick = 0;
    }

    @Override
    public void update() {
        currentTick++;
        for (Rectangle b : bullets) {
            b.center.setX(b.center.getX() + bulletSpeed);
        }
        while (bullets.size() > 0
                && (bullets.peek().center.getX() - bullets.peek().size.getX()) > GameWorld.RIGHT_EDGE) {
            bullets.remove().removeFromAutoDrawSet();
        }
        for (Rectangle b : bullets) {
            for (Enemy e : enemies) {
                if (e.collided(b) && b.visible) {
                    e.gotShot(b.color);
                    b.visible = false;
                }
            }
        }
    }

    // Fire the weapon
    public void fire() {
        if (currentTick >= lastTick + fireRate) {
            Rectangle bullet = new Rectangle();
            bullet.center.set(hero.center);
            bullet.size.set(bulletSize, bulletSize);
            bullet.color = hero.getColor();
            bullets.add(bullet);
            lastTick = currentTick;
        }
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

    public void setEnemies(ArrayList<Enemy> enemies) {
        this.enemies = enemies;
    }
}

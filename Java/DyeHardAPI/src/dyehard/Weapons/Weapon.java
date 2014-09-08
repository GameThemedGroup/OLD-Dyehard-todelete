package dyehard.Weapons;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import Engine.Vector2;
import dyehard.Collidable;
import dyehard.GameObject;
import dyehard.UpdateManager.Updateable;
import dyehard.Enemies.Enemy;
import dyehard.Player.Hero;
import dyehard.Util.Timer;
import dyehard.World.GameWorld;

public class Weapon extends GameObject implements Updateable {
    protected static float bulletSpeed = 1f;
    protected static float bulletSize = 1.5f;
    protected Hero hero;
    protected Queue<GameObject> bullets;
    protected ArrayList<Enemy> enemies;
    // Weapon fires 4 bullets/second, time is in milliseconds
    protected float fireRate = 250f;
    protected Timer timer;

    public Weapon(Hero hero) {
        this.hero = hero;
        bullets = new LinkedList<GameObject>();
        timer = new Timer(fireRate);
    }

    // Fire the weapon
    public void fire() {
        if (timer.isDone()) {
            Bullet bullet = new Bullet();
            bullet.center.set(hero.center);
            bullet.size.set(bulletSize, bulletSize);
            bullet.velocity = new Vector2(bulletSpeed, 0f);
            bullet.color = hero.getColor();
            bullet.dyeColor = hero.getColor();
            timer.reset();
        }
    }

    public void setEnemies(ArrayList<Enemy> enemies) {
        this.enemies = enemies;
    }

    @Override
    public void update() {
        return;
    }

    @Override
    public boolean isActive() {
        return hero.isActive();
    }

    public class Bullet extends Collidable {
        public Color dyeColor;

        @Override
        public void update() {
            super.update();
            if (center.getX() - size.getX() > GameWorld.RIGHT_EDGE) {
                destroy();
            }
        }

        @Override
        public void handleCollision(Collidable other) {
            if (other instanceof Enemy) {
                Enemy enemy = (Enemy) other;
                enemy.setColor(dyeColor);
                destroy();
            }
        }
    }

    @Override
    public String toString() {
        return "Default";
    }
}

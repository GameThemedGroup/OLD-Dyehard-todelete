package dyehard.Weapons;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import dyehard.GameObject;
import dyehard.ManagerState;
import dyehard.UpdateManager.Updateable;
import dyehard.Enemies.Enemy;
import dyehard.Player.DyeMeter.Progressable;
import dyehard.Player.Hero;
import dyehard.Util.Timer;

public class Weapon extends GameObject implements Updateable, Progressable {
    protected static float bulletSpeed = 1f;
    // Was 0.75f, but it was a radius and we are not
    // working with circles
    protected static float bulletSize = 1.5f;
    protected Hero hero;
    protected Queue<GameObject> bullets;
    protected ArrayList<Enemy> enemies;
    // Weapon fires 4 bullets/second, time is in milliseconds
    protected final float fireRate = 250f;
    protected Timer timer;

    public Weapon(Hero hero) {
        this.hero = hero;
        bullets = new LinkedList<GameObject>();
        timer = new Timer(fireRate);
    }

    // Fire the weapon
    public void fire() {
        if (timer.isDone()) {
            new Bullet(hero);
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
    public ManagerState updateState() {
        return hero.updateState();
    }

    @Override
    public String toString() {
        return "Default";
    }

    @Override
    public int currentValue() {
        return 1;
    }

    @Override
    public int totalValue() {
        return 1;
    }
}

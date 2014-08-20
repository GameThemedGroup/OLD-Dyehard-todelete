package dyehard.Weapons;

import Engine.Vector2;
import dyehard.UpdateObject;
import dyehard.Player.Hero;
import dyehard.Util.Timer;

public class Weapon extends UpdateObject {
    protected static float bulletSpeed = 1f;
    protected static float bulletSize = 1.5f;
    protected Hero hero;

    // Weapon fires 4 bullets/second, time is in milliseconds
    protected final float fireRate = 250f;
    protected Timer timer;

    public Weapon(Hero hero) {
        this.hero = hero;
        timer = new Timer(fireRate);
    }

    // Fire the weapon
    public void fire() {
        if (timer.isDone() && !hero.isUnarmed) {
            Projectile bullet = new Projectile();
            bullet.center.set(hero.center);
            bullet.size.set(bulletSize, bulletSize);
            bullet.color = hero.getColor();
            bullet.velocity = new Vector2(bulletSpeed, 0f);
            timer.reset();
        }
    }

    @Override
    public String toString() {
        return "Default";
    }

    @Override
    public boolean isActive() {
        // Weapons are always active
        return true;
    }

    @Override
    public void update() {

    }
}

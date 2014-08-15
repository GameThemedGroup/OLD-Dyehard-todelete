package dyehard.Weapons;

import Engine.Vector2;
import dyehard.Player.Hero;

public class SpreadFireWeapon extends Weapon {

    public SpreadFireWeapon(Hero hero) {
        super(hero);
    }

    @Override
    public void fire() {
        if (timer.isDone() && !hero.isUnarmed) {
            Projectile bullet = new Projectile();
            bullet.center = new Vector2(hero.center);
            bullet.size.set(bulletSize, bulletSize);
            bullet.color = hero.getColor();
            bullet.velocity = new Vector2(bulletSpeed, bulletSpeed / 2);

            bullet = new Projectile();
            bullet.center = new Vector2(hero.center);
            bullet.size.set(bulletSize, bulletSize);
            bullet.color = hero.getColor();
            bullet.velocity = new Vector2(bulletSpeed, -bulletSpeed / 2);

            bullet = new Projectile();
            bullet.center = new Vector2(hero.center);
            bullet.size.set(bulletSize, bulletSize);
            bullet.color = hero.getColor();
            bullet.velocity = new Vector2(bulletSpeed, 0f);

            timer.reset();
        }
    }

    @Override
    public String toString() {
        return "Spread fire";
    }
}

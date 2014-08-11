package dyehard.Weapons;

import dyehard.Player.Hero;

public class LimitedAmmoWeapon extends Weapon {
    private int reloadAmount = 10;
    private int ammo;

    public LimitedAmmoWeapon(Hero hero) {
        super(hero);
        ammo = 10;
    }

    public void recharge() {
        ammo = reloadAmount;
    }

    @Override
    public void fire() {
        if (timer.isDone() && ammo > 0 && !hero.isUnarmed) {
            super.fire();
            if (!hero.isOverloaded) {
                ammo--;
            }
        }
    }

    @Override
    public String toString() {
        return "Limited Ammo " + ammo + "/10";
    }
}

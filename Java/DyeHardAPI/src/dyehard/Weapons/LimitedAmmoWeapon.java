package dyehard.Weapons;

import dyehard.Player.Hero;

public class LimitedAmmoWeapon extends Weapon {
    private final int reloadAmount = 10;
    private final int maxAmmo = 10;
    private int currentAmmo;

    public LimitedAmmoWeapon(Hero hero) {
        super(hero);
        currentAmmo = maxAmmo;
    }

    public void reload() {
        currentAmmo += reloadAmount;

        if (currentAmmo > maxAmmo) {
            currentAmmo = maxAmmo;
        }
    }

    @Override
    public void fire() {
        if (timer.isDone() && currentAmmo > 0) {
            super.fire();
            currentAmmo--;
        }
    }

    @Override
    public String toString() {
        return "Limited Ammo " + currentAmmo + "/" + maxAmmo;
    }
}

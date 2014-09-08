package dyehard.Weapons;

import dyehard.Configuration;
import dyehard.Player.Hero;

public class LimitedAmmoWeapon extends Weapon {
    private final int reloadAmount = Configuration.limitedReloadAmount;
    private final int maxAmmo = Configuration.limitedMaxAmmo;
    private int currentAmmo;

    public LimitedAmmoWeapon(Hero hero) {
        super(hero);
        setFireRate(Configuration.limitedFiringRate);
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

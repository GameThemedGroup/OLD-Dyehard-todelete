package dyehard.Weapons;

import dyehard.Player.Hero;

public class LimitedAmmoWeapon extends Weapon {
    private int reloadAmount = 10;
    public int ammo;
    public int totalAmmo = 10;

    public LimitedAmmoWeapon(Hero hero) {
        super(hero);
        ammo = totalAmmo;
    }

    public void recharge() {
        ammo = reloadAmount;
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void fire() {
        if (timer.isDone() && ammo > 0) {
            super.fire();
            ammo--;
        }
    }

    @Override
    public int currentValue() {
        return ammo;
    }

    @Override
    public int totalValue() {
        return totalAmmo;
    }

    @Override
    public String toString() {
        return "Limited Ammo " + ammo + "/" + reloadAmount;
    }
}

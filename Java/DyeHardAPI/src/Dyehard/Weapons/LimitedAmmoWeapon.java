package dyehard.Weapons;

import java.awt.Color;

import Engine.Rectangle;
import Engine.Vector2;
import dyehard.Player.Hero;
import dyehard.World.GameWorld;

public class LimitedAmmoWeapon extends Weapon {
    private int reloadAmount = 10;
    private int ammo;
    private Rectangle ammoTracker;

    public LimitedAmmoWeapon(Hero hero) {
        super(hero);
        ammo = 10;
        ammoTracker = new Rectangle();
        ammoTracker.center = new Vector2(GameWorld.LEFT_EDGE + 8,
                GameWorld.TOP_EDGE - 4);
        ammoTracker.size.set(4, 4);
    }

    public void recharge() {
        ammo = reloadAmount;
    }

    @Override
    public void update() {
        if (ammo == 0) {
            ammoTracker.color = Color.RED;
        } else {
            ammoTracker.color = Color.GREEN;
        }
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
    public String toString() {
        return "Limited Ammo " + ammo + "/" + reloadAmount;
    }
}

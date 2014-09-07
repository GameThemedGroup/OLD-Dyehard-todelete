package dyehard.Collectibles;

import Engine.BaseCode;
import dyehard.Player.Hero;
import dyehard.Weapons.LimitedAmmoWeapon;
import dyehard.Weapons.OverHeatWeapon;

public class Overload extends PowerUp {
    // public static PowerUpMeter meter = new PowerUpMeter(2, Game.Pink);
    public Overload() {
        super();
        timer.setInterval(duration);
        texture = BaseCode.resources.loadImage("Textures/PowerUp_Red.png");
        applicationOrder = 30;
        label.setText("Overload");
    }

    @Override
    public void apply(Hero hero) {
        if (hero.currentWeapon instanceof OverHeatWeapon) {
            ((OverHeatWeapon) hero.currentWeapon).currentHeatLevel = 0f;
            ((OverHeatWeapon) hero.currentWeapon).overheated = false;
        } else if (hero.currentWeapon instanceof LimitedAmmoWeapon) {
            ((LimitedAmmoWeapon) hero.currentWeapon).reload();
        }
    }

    @Override
    public void unapply(Hero hero) {
        // TODO Auto-generated method stub

    }

    @Override
    public PowerUp clone() {
        return new Overload();
    }

    @Override
    public String toString() {
        return "Overload: " + super.toString();
    }
}

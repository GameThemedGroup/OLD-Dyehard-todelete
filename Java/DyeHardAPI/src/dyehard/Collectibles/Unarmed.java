package dyehard.Collectibles;

import Engine.BaseCode;
import dyehard.Configuration;
import dyehard.Player.Hero;
import dyehard.Weapons.BrokenWeapon;

public class Unarmed extends PowerUp {

    public Unarmed() {
        super();
        duration = Configuration
                .getPowerUpData(Configuration.PowerUpType.UNARMED).duration * 1000;
        texture = BaseCode.resources.loadImage("Textures/PowerUp_Unarmed.png");
        timer.setInterval(duration);
        applicationOrder = 0;
        // label.setText("Unarmed");
    }

    @Override
    public void apply(Hero hero) {
        hero.currentWeapon = new BrokenWeapon(hero);
    }

    @Override
    public void unapply(Hero hero) {
        hero.currentWeapon = hero.defaultWeapon;
    }

    @Override
    public PowerUp clone() {
        return new Unarmed();
    }

    @Override
    public String toString() {
        return "Unarmed: " + super.toString();
    }
}

package dyehard.Collectibles;

import dyehard.DHR;
import dyehard.Player.Hero;
import dyehard.Weapons.BrokenWeapon;

public class Unarmed extends PowerUp {

    protected float duration = DHR.getPowerupData(DHR.PowerupID.PU_UNARMED).duration * 1000;

    public Unarmed() {
        super();
        timer.setInterval(duration);
        applicationOrder = 0;
        label.setText("Unarmed");
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

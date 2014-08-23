package dyehard.Collectibles;

import dyehard.Player.Hero;
import dyehard.Weapons.BrokenWeapon;

public class Unarmed extends PowerUp {

    public Unarmed() {
        applicationOrder = 0;
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
        return super.toString() + " Unarmed";
    }
}

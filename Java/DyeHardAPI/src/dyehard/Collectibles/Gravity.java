package dyehard.Collectibles;

import dyehard.Player.Hero;

public class Gravity extends PowerUp {

    public Gravity() {
        applicationOrder = 2;
        label.setText("Gravity");
    }

    @Override
    public void apply(Hero hero) {
        hero.currentGravity.set(0f, -1.5f);
    }

    @Override
    public void unapply(Hero hero) {
        hero.currentGravity.set(0f, 0f);
    }

    @Override
    public PowerUp clone() {
        return new Gravity();
    }

    @Override
    public String toString() {
        return super.toString() + " Gravity";
    }

}

package dyehard.Collectibles;

import java.awt.Color;

import dyehard.Player.Hero;

public class Gravity extends PowerUp {
    public Gravity(Hero hero) {
        super(hero);
        color = Color.red;
        label.setText("Gravity");

        usageOrder = 50;
    }

    @Override
    public void activate() {
        super.activate();
    }

    @Override
    public void apply() {
        hero.gravityOn();
    }

    @Override
    public void unapply() {
        hero.gravityOff();
    }

    @Override
    public String toString() {
        return super.toString() + " Gravity";
    }
}

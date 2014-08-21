package dyehard.Collectibles;

import java.awt.Color;

import dyehard.Player.Hero;

public class Magnetism extends PowerUp {
    public Magnetism(Hero hero, float minX, float maxX) {
        super(hero, minX, maxX);
        color = Color.white;
        label.setText("Magnet");

        usageOrder = 40;
    }

    @Override
    public void apply() {
        hero.magnetismOn();
    }

    @Override
    public void unapply() {
        hero.magnetismOff();
    }

    @Override
    public String toString() {
        return "Magnetism: " + super.toString();
    }
}

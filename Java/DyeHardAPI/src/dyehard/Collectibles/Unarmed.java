package dyehard.Collectibles;

import java.awt.Color;

import dyehard.Player.Hero;

public class Unarmed extends PowerUp {
    public Unarmed(Hero hero, float minX, float maxX) {
        super(hero, minX, maxX);
        color = Color.cyan;
        label.setText("Unarmed");

        usageOrder = 0;
    }

    @Override
    public void apply() {
        hero.unarmedOn();
    }

    @Override
    public void unapply() {
        hero.unarmedOff();
    }

    @Override
    public String toString() {
        return super.toString() + " Unarmed";
    }
}

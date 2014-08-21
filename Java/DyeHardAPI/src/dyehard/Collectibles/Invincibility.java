package dyehard.Collectibles;

import java.awt.Color;

import Engine.BaseCode;
import dyehard.Player.Hero;

public class Invincibility extends PowerUp {
    public Invincibility(Hero hero) {
        super(hero);
        label.setText("Invin");
        label.setFrontColor(Color.white);
        texture = BaseCode.resources.loadImage("Textures/PowerUp_Pink.png");

        usageOrder = 100;
    }

    @Override
    public void apply() {
        hero.invincibilityOn();
    }

    @Override
    public void unapply() {
        hero.invincibilityOff();
    }

    @Override
    public String toString() {
        return super.toString() + " Invincibility";
    }
}

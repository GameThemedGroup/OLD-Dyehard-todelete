package dyehard.Collectibles;

import java.awt.Color;

import Engine.BaseCode;
import dyehard.Player.Hero;

public class Overload extends PowerUp {
    public Overload(Hero hero, float minX, float maxX) {
        super(hero, minX, maxX);
        label.setText("Overload");
        label.setFrontColor(Color.white);
        texture = BaseCode.resources.loadImage("Textures/PowerUp_Red.png");

        usageOrder = 30;
    }

    @Override
    public void apply() {
        hero.overloadOn();
    }

    @Override
    public void unapply() {
        hero.overloadOff();
    }

    @Override
    public String toString() {
        return "Overload: " + super.toString();
    }
}

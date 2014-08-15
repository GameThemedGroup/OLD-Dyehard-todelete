package dyehard.Collectibles;

import java.awt.Color;

import Engine.BaseCode;
import dyehard.Player.Hero;

public class Ghost extends PowerUp {
    public Ghost(Hero hero, float minX, float maxX) {
        super(hero, minX, maxX);
        label.setText("Ghost");
        label.setFrontColor(Color.white);
        texture = BaseCode.resources.loadImage("Textures/PowerUp_Blue.png");

        usageOrder = 90;
    }

    @Override
    public void apply() {
        hero.ghostOn();
    }

    @Override
    public void unapply() {
        hero.ghostOff();
    }

    @Override
    public String toString() {
        return super.toString() + " Ghost";
    }
}

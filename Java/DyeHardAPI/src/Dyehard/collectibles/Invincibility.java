package dyehard.Collectibles;

import java.awt.Color;

import Engine.BaseCode;
import dyehard.Player.Hero;

public class Invincibility extends PowerUp {
    public Invincibility(Hero hero, float minX, float maxX) {
        super(hero, minX, maxX);
        label.setText("Invin");
        label.setFrontColor(Color.white);
        texture = BaseCode.resources.loadImage("Textures/PowerUp_Pink.png");
    }

    @Override
    public void activate() {
        hero.invincibilityOn();
        PowerUpManager.InvincibilityTimer.reset();
        super.activate();
    }

    @Override
    public String toString() {
        return "Invincibility";
    }
}

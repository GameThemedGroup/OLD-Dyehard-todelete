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
    }

    @Override
    public void activate() {
        hero.ghostOn();
        PowerUpManager.GhostTimer.reset();
        super.activate();
    }

    @Override
    public String toString() {
        return "Ghost";
    }
}

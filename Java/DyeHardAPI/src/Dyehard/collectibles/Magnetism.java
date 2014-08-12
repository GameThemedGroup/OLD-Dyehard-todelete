package dyehard.Collectibles;

import java.awt.Color;

import dyehard.Player.Hero;

public class Magnetism extends PowerUp {
    public Magnetism(Hero hero, float minX, float maxX) {
        super(hero, minX, maxX);
        color = Color.white;
        // texture = BaseCode.resources.loadImage("Textures/PowerUp_Pink.png");
    }

    @Override
    public void activate() {
        hero.magnetismOn();
        PowerUpManager.MagnetismTimer.reset();
        super.activate();
    }

    @Override
    public String toString() {
        return "Magnetism";
    }
}

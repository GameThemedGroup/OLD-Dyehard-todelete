package Dyehard.collectibles;

import Dyehard.Player.Hero;
import Engine.BaseCode;

public class Invincibility extends PowerUp {
    // public static PowerUpMeter meter = new PowerUpMeter(2, Game.Pink);
    public Invincibility(Hero hero, float minX, float maxX) {
        super(hero, minX, maxX);
        texture = BaseCode.resources.loadImage("Textures/PowerUp_Pink.png");
    }

    @Override
    public void activate() {
        // meter.reset(Duration, null);
        super.activate();
    }
}

package Dyehard.Powerups;

import Dyehard.Player.Hero;
import Engine.BaseCode;

public class SpeedUp extends PowerUp {
    // public static PowerUpMeter meter = new PowerUpMeter(0, DyeHard.Green);
    public SpeedUp(Hero hero, float minX, float maxX) {
        super(hero, minX, maxX);
        box.texture = BaseCode.resources
                .loadImage("Textures/PowerUp_Green.png");
    }

    @Override
    public void activate() {
        hero.increaseSpeed();
        // meter.reset(Duration, hero.normalizeSpeed);
        super.activate();
    }
}

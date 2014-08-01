package dyehard.Collectibles;

import dyehard.Player.Hero;
import Engine.BaseCode;

public class SpeedUp extends PowerUp {
    // public static PowerUpMeter meter = new PowerUpMeter(0, DyeHard.Green);
    public SpeedUp(Hero hero, float minX, float maxX) {
        super(hero, minX, maxX);
        texture = BaseCode.resources.loadImage("Textures/PowerUp_Green.png");
    }

    @Override
    public void activate() {
        hero.increaseSpeed();
        // meter.reset(Duration, hero.normalizeSpeed);
        super.activate();
    }
}

package dyehard.Collectibles;

import Engine.BaseCode;
import dyehard.Player.Hero;

public class SpeedUp extends PowerUp {
    // public static PowerUpMeter meter = new PowerUpMeter(0, DyeHard.Green);
    public SpeedUp(Hero hero) {
        super(hero);
        texture = BaseCode.resources.loadImage("Textures/PowerUp_Green.png");
    }

    @Override
    public void activate() {
        hero.increaseSpeed();
        // meter.reset(Duration, hero.normalizeSpeed);
        super.activate();
    }
}

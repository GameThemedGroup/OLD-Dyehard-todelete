package Dyehard.Powerups;

import Dyehard.Player.Hero;

public class SpeedUp extends PowerUp {
    // public static PowerUpMeter meter = new PowerUpMeter(0, DyeHard.Green);
    public SpeedUp(Hero hero, float minX, float maxX) {
        super(hero, minX, maxX);
        box.setImage("PowerUp_Green");
    }

    @Override
    public void activate() {
        hero.increaseSpeed();
        // meter.reset(Duration, hero.normalizeSpeed);
        super.activate();
    }
}

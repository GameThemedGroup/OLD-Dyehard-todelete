package dyehard.Collectibles;

import Engine.BaseCode;
import dyehard.Player.Hero;

public class SpeedUp extends PowerUp {
    // public static PowerUpMeter meter = new PowerUpMeter(0, DyeHard.Green);
    public SpeedUp() {
        texture = BaseCode.resources.loadImage("Textures/PowerUp_Green.png");
    }

    @Override
    public void activate(Hero hero) {
        hero.increaseSpeed();
        // meter.reset(Duration, hero.normalizeSpeed);
        super.activate(hero);
    }

    @Override
    public void apply(Hero hero) {
        // TODO Auto-generated method stub

    }

    @Override
    public void unapply(Hero hero) {
        // TODO Auto-generated method stub

    }

    @Override
    public PowerUp clone() {
        return new SpeedUp();
    }
}

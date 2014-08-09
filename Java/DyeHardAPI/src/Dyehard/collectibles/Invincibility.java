package dyehard.Collectibles;

import Engine.BaseCode;
import dyehard.Player.Hero;

public class Invincibility extends PowerUp {
    // public static PowerUpMeter meter = new PowerUpMeter(2, Game.Pink);
    public Invincibility() {
        texture = BaseCode.resources.loadImage("Textures/PowerUp_Pink.png");
    }

    @Override
    public void activate(Hero hero) {
        // meter.reset(Duration, null);
        super.activate(hero);
    }
}

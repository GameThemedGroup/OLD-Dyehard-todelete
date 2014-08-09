package dyehard.Collectibles;

import Engine.BaseCode;
import dyehard.Player.Hero;

public class Overload extends PowerUp {
    // public static PowerUpMeter meter = new PowerUpMeter(2, Game.Pink);
    public Overload() {
        texture = BaseCode.resources.loadImage("Textures/PowerUp_Red.png");
    }

    @Override
    public void activate(Hero hero) {
        // meter.reset(Duration, null);
        super.activate(hero);
    }
}

package Dyehard.Powerups;

import Dyehard.Player.Hero;

public class Overload extends PowerUp {
    // public static PowerUpMeter meter = new PowerUpMeter(2, Game.Pink);
    public Overload(Hero hero, float minX, float maxX) {
        super(hero, minX, maxX);
        box.setImage("PowerUp_Red");
    }

    @Override
    public void activate() {
        // meter.reset(Duration, null);
        super.activate();
    }
}

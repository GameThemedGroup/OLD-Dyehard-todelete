package Dyehard.Powerups;

import Dyehard.Player.Hero;

public class Invincibility extends PowerUp {
    // public static PowerUpMeter meter = new PowerUpMeter(2, Game.Pink);
    public Invincibility(Hero hero, float minX, float maxX) {
        super(hero, minX, maxX);
        box.setImage("PowerUp_Pink");
    }

    @Override
    public void activate() {
        // meter.reset(Duration, null);
        super.activate();
    }
}

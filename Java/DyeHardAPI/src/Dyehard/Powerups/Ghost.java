package Dyehard.Powerups;

import Dyehard.Player.Hero;

public class Ghost extends PowerUp {
    // public static PowerUpMeter meter = new PowerUpMeter(1, Game.Blue);
    public Ghost(Hero hero, float minX, float maxX) {
        super(hero, minX, maxX);
        box.setImage("PowerUp_Blue");
    }

    @Override
    public void activate() {
        hero.setInvisible();
        // meter.reset(Duration, hero.setVisible);
        super.activate();
    }
}

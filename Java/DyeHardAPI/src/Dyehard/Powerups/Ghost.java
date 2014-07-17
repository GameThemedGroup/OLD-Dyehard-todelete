package Dyehard.Powerups;

import BaseTypes.PowerUp;
import Dyehard.Player.Hero;
import Engine.BaseCode;

public class Ghost extends PowerUp {
    // public static PowerUpMeter meter = new PowerUpMeter(1, Game.Blue);
    public Ghost(Hero hero, float minX, float maxX) {
        super(hero, minX, maxX);
        texture = BaseCode.resources.loadImage("Textures/PowerUp_Blue.png");
    }

    @Override
    public void activate() {
        hero.setInvisible();
        // meter.reset(Duration, hero.setVisible);
        super.activate();
    }
}

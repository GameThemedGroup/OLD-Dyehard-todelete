package dyehard.Collectibles;

import Engine.BaseCode;
import dyehard.Player.Hero;

public class Ghost extends PowerUp {
    // public static PowerUpMeter meter = new PowerUpMeter(1, Game.Blue);
    public Ghost(Hero hero) {
        super(hero);
        texture = BaseCode.resources.loadImage("Textures/PowerUp_Blue.png");
    }

    @Override
    public void activate() {
        hero.setInvisible();
        // meter.reset(Duration, hero.setVisible);
        super.activate();
    }
}

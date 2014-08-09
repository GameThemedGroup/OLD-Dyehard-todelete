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
        return new Invincibility();
    }
}

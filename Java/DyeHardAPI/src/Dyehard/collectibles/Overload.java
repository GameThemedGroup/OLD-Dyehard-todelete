package dyehard.Collectibles;

import Engine.BaseCode;
import dyehard.Player.Hero;

public class Overload extends PowerUp {
    // public static PowerUpMeter meter = new PowerUpMeter(2, Game.Pink);
    public Overload() {
        texture = BaseCode.resources.loadImage("Textures/PowerUp_Red.png");
        applicationOrder = 100;
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
        return new Overload();
    }

    @Override
    public String toString() {
        return "Overload";
    }
}

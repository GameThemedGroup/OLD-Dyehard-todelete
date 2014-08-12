package dyehard.Collectibles;

import Engine.BaseCode;
import dyehard.Player.Hero;

public class Overload extends PowerUp {
    public Overload(Hero hero, float minX, float maxX) {
        super(hero, minX, maxX);
        texture = BaseCode.resources.loadImage("Textures/PowerUp_Red.png");
    }

    @Override
    public void activate() {
        hero.overloadOn();
        PowerUpManager.OverloadTimer.reset();
        super.activate();
    }

    @Override
    public String toString() {
        return "Overload";
    }
}

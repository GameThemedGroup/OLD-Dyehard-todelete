package dyehard.Collectibles;

import Engine.BaseCode;
import dyehard.Player.Hero;

public class SpeedUp extends PowerUp {
    public SpeedUp(Hero hero, float minX, float maxX) {
        super(hero, minX, maxX);
        texture = BaseCode.resources.loadImage("Textures/PowerUp_Green.png");
    }

    @Override
    public void activate() {
        hero.increaseSpeed();
        PowerUpManager.SpeedUpTimer.reset();
        super.activate();
    }
}

package dyehard.Collectibles;

import Engine.BaseCode;
import dyehard.Player.Hero;

public class Invincibility extends PowerUp {
    public Invincibility(Hero hero, float minX, float maxX) {
        super(hero, minX, maxX);
        texture = BaseCode.resources.loadImage("Textures/PowerUp_Pink.png");
    }

    @Override
    public void activate() {
        hero.invincibilityOn();
        PowerUpManager.InvincibilityTimer.reset();
        super.activate();
    }

    @Override
    public String toString() {
        return "Invincibility";
    }
}

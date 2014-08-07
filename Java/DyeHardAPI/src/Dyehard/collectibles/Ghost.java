package dyehard.Collectibles;

import Engine.BaseCode;
import dyehard.Player.Hero;

public class Ghost extends PowerUp {
    public Ghost(Hero hero, float minX, float maxX) {
        super(hero, minX, maxX);
        texture = BaseCode.resources.loadImage("Textures/PowerUp_Blue.png");
    }

    @Override
    public void activate() {
        hero.ghostOn();
        PowerUpManager.GhostTimer.reset();
        super.activate();
        System.out.println("Picked up Ghost");
    }
}

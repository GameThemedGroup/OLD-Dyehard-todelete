package dyehard.Enemies;

import Engine.Vector2;
import dyehard.Player.Hero;

public class SpiderEnemy extends Enemy {
    public SpiderEnemy(Vector2 center, float height, Hero currentHero) {
        super(center, height, height, currentHero,
                "Textures/minion_Shooter.png");
    }
}
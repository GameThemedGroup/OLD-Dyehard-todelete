package dyehard.Enemies;

import Engine.Vector2;
import dyehard.Player.Hero;

public class SpiderEnemy extends Enemy {
    private static float widthToHeightRatio = 0.985f;

    public SpiderEnemy(Vector2 center, float height, Hero currentHero) {
        super(center, height * widthToHeightRatio, height, currentHero,
                "Textures/Spider Enemy.png");
    }
}
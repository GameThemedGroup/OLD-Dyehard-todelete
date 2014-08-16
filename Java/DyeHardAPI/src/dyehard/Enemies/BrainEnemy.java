package dyehard.Enemies;

import Engine.Vector2;
import dyehard.Player.Hero;

public class BrainEnemy extends Enemy {
    private static float widthToHeightRatio = 0.59f;

    public BrainEnemy(Vector2 center, float height, Hero currentHero) {
        super(center, height * widthToHeightRatio, height, currentHero,
                "Textures/Brain Enemy.png");
    }
}
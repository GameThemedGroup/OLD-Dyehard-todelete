package dyehard.Enemies;

import Engine.Vector2;
import dyehard.Player.Hero;

public class RedBeamEnemy extends Enemy {
    private static float widthToHeightRatio = 0.7f;

    public RedBeamEnemy(Vector2 center, float height, Hero currentHero) {
        super(center, height * widthToHeightRatio, height, currentHero,
                "Textures/Red Beam Enemy.png");
    }

    @Override
    public String toString() {
        return "Red Beam";
    }
}
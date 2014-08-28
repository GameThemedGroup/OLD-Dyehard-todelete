package dyehard.Enemies;

import Engine.Vector2;
import dyehard.Player.Hero;

public class CollectorEnemy extends Enemy {
    private static float widthToHeightRatio = 1f;

    public CollectorEnemy(Vector2 center, float height, Hero currentHero) {
        super(center, height * widthToHeightRatio, height, currentHero,
                "Textures/Enemies/minion_collector.png");
    }

    @Override
    public String toString() {
        return "Red Beam";
    }
}
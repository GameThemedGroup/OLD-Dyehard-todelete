package dyehard.Enemies;

import Engine.Vector2;
import dyehard.Player.Hero;

public class ShootingEnemy extends Enemy {
    private static float widthToHeightRatio = 1f;

    public ShootingEnemy(Vector2 center, float height, Hero currentHero) {
        super(center, height * widthToHeightRatio, height, currentHero,
                "Textures/Enemies/minion_shooter.png");
    }

    @Override
    public String toString() {
        return "Spider";
    }
}
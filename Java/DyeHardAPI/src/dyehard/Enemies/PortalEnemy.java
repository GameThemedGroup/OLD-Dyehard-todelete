package dyehard.Enemies;

import Engine.Vector2;
import dyehard.Player.Hero;

public class PortalEnemy extends Enemy {
    private static float widthToHeightRatio = 1f;

    public PortalEnemy(Vector2 center, float height, Hero currentHero) {
        super(center, height * widthToHeightRatio, height, currentHero,
                "Textures/Enemies/minion_Portal.png");
    }

    @Override
    public String toString() {
        return "Portal";
    }
}
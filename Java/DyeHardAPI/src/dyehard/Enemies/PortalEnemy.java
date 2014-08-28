package dyehard.Enemies;

import Engine.Vector2;
import dyehard.Player.Hero;
import dyehard.Util.Timer;

public class PortalEnemy extends Enemy {
    private static float widthToHeightRatio = 1f;
    private Timer timer;

    public PortalEnemy(Vector2 center, float height, Hero currentHero) {
        super(center, height * widthToHeightRatio, height, currentHero,
                "Textures/Enemies/minion_Portal.png");
        timer = new Timer(2000f);
    }

    @Override
    public void update() {
        super.update();
        if (timer.isDone()) {
            Portal blackHole = new Portal(center.clone(), 4f, hero);
            timer.reset();
        }
    }

    @Override
    public String toString() {
        return "Portal";
    }
}
package dyehard.Enemies;

import Engine.Vector2;
import dyehard.Player.Hero;
import dyehard.Util.Timer;

public class PortalEnemy extends Enemy {
    private Timer timer;
    private static float width = 10f;
    private static float height = 10f;
    private static float behaviorChangeTime = 3000f;
    private static float baseSpeed = 0.3f;

    public PortalEnemy(Vector2 center, float newWidth, float newHeight,
            float changeTime, float newSpeed, Hero currentHero) {
        super(center, newWidth, newHeight, changeTime, newSpeed, currentHero,
                "Textures/Enemies/minion_portal.png");
        timer = new Timer(2000f);
    }

    public PortalEnemy(Vector2 center, Hero currentHero) {
        super(center, width, height, behaviorChangeTime, baseSpeed,
                currentHero, "Textures/Enemies/minion_portal.png");
        timer = new Timer(2000f);
    }

    @Override
    public void update() {
        super.update();
        if (timer.isDone()) {
            new Portal(center.clone(), 4f, hero);
            timer.reset();
        }
    }

    @Override
    public String toString() {
        return "Portal";
    }

    public static void setAttributes(float w, float h, float changeTime, float s) {
        width = w;
        height = h;
        behaviorChangeTime = changeTime;
        baseSpeed = s;
    }
}
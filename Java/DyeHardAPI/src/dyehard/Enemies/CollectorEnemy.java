package dyehard.Enemies;

import Engine.Vector2;
import dyehard.Player.Hero;

public class CollectorEnemy extends Enemy {
    private static float width = 10f;
    private static float height = 10f;
    private static float behaviorChangeTime = 3000f;
    private static float baseSpeed = 0.3f;

    public CollectorEnemy(Vector2 center, float newWidth, float newHeight,
            float changeTime, float newSpeed, Hero currentHero) {
        super(center, newWidth, newHeight, changeTime, newSpeed, currentHero,
                "Textures/Enemies/minion_collector.png");
    }

    public CollectorEnemy(Vector2 center, Hero currentHero) {
        super(center, width, height, behaviorChangeTime, baseSpeed,
                currentHero, "Textures/Enemies/minion_collector.png");
    }

    @Override
    public String toString() {
        return "Collector";
    }

    public static void setAttributes(float w, float h, float changeTime, float s) {
        width = w;
        height = h;
        behaviorChangeTime = changeTime;
        baseSpeed = s;
    }
}
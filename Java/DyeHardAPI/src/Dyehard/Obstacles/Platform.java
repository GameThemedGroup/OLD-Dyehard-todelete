package Dyehard.Obstacles;

import java.awt.Color;
import java.util.Random;

import Dyehard.World.GameWorld;
import Engine.Vector2;

public class Platform {
    private static int SEGMENT_COUNT = 30;
    private static float WIDTH = Stargate.WIDTH / SEGMENT_COUNT;
    public static float height = 1.25f;
    private static Random RANDOM = new Random();
    private final float mask = 0.1f; // overlap between platform segments

    public Platform(int offset, float leftEdge, boolean continuous) {
        fillPlatform(offset, leftEdge, continuous);
    }

    private void fillPlatform(int offset, float leftEdge, boolean continuous) {
        // set up platform
        float Ypos = ((offset * 1f) / Stargate.GATE_COUNT) * GameWorld.TOP_EDGE;

        if (continuous) {
            float Xpos = leftEdge + (Stargate.WIDTH / 2);
            createPlatform(new Vector2(Xpos, Ypos), Stargate.WIDTH);
        } else {
            // randomly fill platform
            int consecutiveChance = 10;
            boolean platform = true;
            for (int i = 0; i < SEGMENT_COUNT; i++) {
                if (platform) {
                    float Xpos = (WIDTH * 0.5f) + leftEdge + (i * WIDTH);
                    createPlatform(new Vector2(Xpos, Ypos), WIDTH);
                }
                consecutiveChance -= 2;
                if (consecutiveChance <= 0
                        || RANDOM.nextInt(consecutiveChance) == 0) {
                    platform = !platform;
                    consecutiveChance = 10;
                }
            }
        }
    }

    private void createPlatform(Vector2 center, float width) {
        Vector2 size = new Vector2(width + mask, height);
        Color color = new Color(112, 138, 144);
        Obstacle platform = new Obstacle(center, size, color);
        ObstacleManager.registerObstacle(platform);
    }
}

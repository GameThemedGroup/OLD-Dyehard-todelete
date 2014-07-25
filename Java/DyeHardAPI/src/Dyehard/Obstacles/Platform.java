package Dyehard.Obstacles;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import BaseTypes.Actor;
import BaseTypes.Enemy;
import Dyehard.Player.Hero;
import Dyehard.World.GameWorld;
import Engine.Rectangle;
import Engine.Vector2;

public class Platform {
    private final int SEGMENT_COUNT = 30;
    public static float height = 1.25f;
    private final float mask = 0.1f; // overlap between platform segments
    private List<Actor> characters;
    private List<Debris> platforms;

    public Platform(int offset, Hero hero, ArrayList<Enemy> enemies,
            float leftEdge, boolean continuous) {
        characters = new ArrayList<Actor>();
        characters.add(hero);
        characters.addAll(enemies);
        platforms = new ArrayList<Obstacle>();
        fillPlatform(offset, leftEdge, continuous);
    }

    public void update() {
        for (Rectangle r : platforms) {
            r.update();
        }
    }

    public void destroy() {
        for (Rectangle r : platforms) {
            r.destroy();
        }
    }

    private void fillPlatform(int offset, float leftEdge, boolean continuous) {
        // set up platform
        float Ypos = ((offset * 1f) / Stargate.GATE_COUNT) * GameWorld.TOP_EDGE;
        float width = Stargate.width / SEGMENT_COUNT;
        if (continuous) {
            float Xpos = leftEdge + (Stargate.width / 2);
            platforms.add(createPlatform(new Vector2(Xpos, Ypos)));
        } else {
            // randomly fill platform
            int consecutiveChance = 10;
            boolean platform = true;
            for (int i = 0; i < SEGMENT_COUNT; i++) {
                Random rand = new Random();
                if (platform) {
                    float Xpos = (width * 0.5f) + leftEdge + (i * width);
                    platforms.add(createPlatform(new Vector2(Xpos, Ypos)));
                }
                consecutiveChance -= 2;
                if (consecutiveChance <= 0
                        || rand.nextInt(consecutiveChance) == 0) {
                    platform = !platform;
                    consecutiveChance = 10;
                } else if (rand.nextInt(consecutiveChance) == 0) {
                    platform = !platform;
                    consecutiveChance = 10;
                }
            }
        }
    }

    private Obstacle createPlatform(Vector2 center) {
        float width = Stargate.width / SEGMENT_COUNT;
        Vector2 size = new Vector2(width + mask, height);
        Color color = new Color(112, 138, 144);
        Obstacle debris = new Obstacle(characters, center, size, color);
        return debris;
    }
}

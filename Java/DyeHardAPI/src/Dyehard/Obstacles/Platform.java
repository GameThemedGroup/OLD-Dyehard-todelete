package Dyehard.Obstacles;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import BaseTypes.Character;
import BaseTypes.Enemy;
import Dyehard.Player.Hero;
import Dyehard.Util.Collision;
import Dyehard.World.GameWorld;
import Engine.Rectangle;
import Engine.Vector2;
import Engine.World.BoundCollidedStatus;

public class Platform extends Rectangle {
    private final int SEGMENT_COUNT = 30;
    public static float height = 1.25f;
    private final float mask = 0.1f; // overlap between platform segments
    private List<Character> characters;

    public Platform(int offset, Hero hero, ArrayList<Enemy> enemies,
            float leftEdge, boolean continuous) {
        // continuous = false;
        characters = new ArrayList<Character>();
        characters.add(hero);
        characters.addAll(enemies);
        velocity = new Vector2(-GameWorld.Speed, 0f);
        shouldTravel = true;
        color = new Color(112, 138, 144);
        fillPlatform(offset, leftEdge, continuous);
    }

    @Override
    public void update() {
        super.update();
        checkCollisions();
    }

    private void fillPlatform(int offset, float leftEdge, boolean continuous) {
        // set up platform
        float Ypos = ((offset * 1f) / Stargate.GATE_COUNT) * GameWorld.TOP_EDGE;
        if (continuous) {
            float Xpos = leftEdge + (Stargate.width / 2);
            center.set(Xpos, Ypos);
            size.set(Stargate.width + mask, height);
        } else {
            // randomly fill platform
            float width = Stargate.width / SEGMENT_COUNT;
            int consecutiveChance = 10;
            boolean platform = true;
            for (int i = 0; i < SEGMENT_COUNT; i++) {
                Random rand = new Random();
                if (platform) {
                    float Xpos = (width * 0.5f) + leftEdge + (i * width);
                    center.set(Xpos, Ypos);
                    size.set(width + mask, height);
                }
                consecutiveChance -= 2;
                if (consecutiveChance > 0
                        && rand.nextInt(consecutiveChance) == 0) {
                    platform = !platform;
                    consecutiveChance = 10;
                }
            }
        }
    }

    private void checkCollisions() {
        // The obstacle is destroyed once it leaves the map through the left,
        // top, or bottom portion of the map.
        BoundCollidedStatus collisionStatus = collideWorldBound();
        if (!isInsideWorldBound()
                && collisionStatus != BoundCollidedStatus.RIGHT) {
            destroy();
            return;
        }
        // Check collisions with each character and push them out of the
        // obstacle. This causes the player and enemy units to glide along the
        // edges of the obstacle
        for (Character c : characters) {
            Vector2 out = new Vector2(0, 0);
            if (Collision.isOverlap(c, this, out)) {
                // Move the character so that it's no longer overlapping the
                // debris
                c.center.add(out);
                // Stop the character from moving if they collide with the
                // debris
                if (Math.abs(out.getX()) > 0.01f) {
                    if (Math.signum(out.getX()) != Math.signum(c.velocity
                            .getX())) {
                        c.velocity.setX(0f);
                    }
                }
                if (Math.abs(out.getY()) > 0.01f) {
                    if (Math.signum(out.getY()) != Math.signum(c.velocity
                            .getY())) {
                        c.velocity.setY(0f);
                    }
                }
            }
        }
    }
}

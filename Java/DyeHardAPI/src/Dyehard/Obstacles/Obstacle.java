package Dyehard.Obstacles;

import java.awt.Color;
import java.util.List;
import java.util.Random;

import BaseTypes.Actor;
import Dyehard.Util.Collision;
import Dyehard.World.GameWorld;
import Engine.BaseCode;
import Engine.Rectangle;
import Engine.Vector2;
import Engine.World.BoundCollidedStatus;

/**
 * Obstacles represent objects that friendly and enemy units are unable to pass.
 * Obstacles can range from moving platforms that the player is able to jump
 * onto to walls that can be combined to form a maze.
 * 
 * @author Rodelle Ladia Jr.
 * 
 */
public class Obstacle extends Rectangle {
    private static float height = 6f;
    List<Actor> characters;

    public Obstacle(List<Actor> characters, float minX, float maxX) {
        float padding = height;
        Random rand = new Random();
        float randomX = (maxX - padding - minX + padding) * rand.nextFloat()
                + minX + padding;
        float randomY = (GameWorld.TOP_EDGE - padding - GameWorld.BOTTOM_EDGE + padding)
                * rand.nextFloat() + 0f + padding;
        center.set(new Vector2(randomX, randomY));
        size.set(height, height);
        velocity = new Vector2(-GameWorld.Speed, 0f);
        shouldTravel = true;
        color = Color.GREEN;
        this.characters = characters;
        switch (rand.nextInt(3)) {
        case 0:
            texture = BaseCode.resources.loadImage("Textures/Beak.png");
            break;
        case 1:
            texture = BaseCode.resources.loadImage("Textures/Window.png");
            break;
        case 2:
            texture = BaseCode.resources.loadImage("Textures/Wing2.png");
            size.setX(size.getY() * 1.8f);
            break;
        }
    }

    public Obstacle(List<Actor> characters, Vector2 center, Vector2 size,
            Color color) {
        this.center = center;
        this.size = size;
        velocity = new Vector2(-GameWorld.Speed, 0f);
        shouldTravel = true;
        this.color = color;
        this.characters = characters;
        this.color = color;
    }

    @Override
    public void update() {
        super.update();
        checkCollisions();
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
        for (Actor c : characters) {
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

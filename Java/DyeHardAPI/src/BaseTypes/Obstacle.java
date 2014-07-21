package BaseTypes;

import java.awt.Color;
import java.util.List;

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
    List<Character> characters;

    public Obstacle(List<Character> characters, Vector2 center, Vector2 size,
            Vector2 velocity) {
        this.center = center;
        this.size = size;
        this.velocity = velocity;
        shouldTravel = true;
        color = Color.GREEN;
        this.characters = characters;
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
        for (Character c : characters) {
            if (collided(c)) {
                pushOutCircle(c);
            }
        }
    }
}

package dyehard;

import java.awt.Color;

import Engine.Vector2;
import dyehard.Obstacles.Obstacle;
import dyehard.Util.Collision;
import dyehard.Util.Colors;

public class Actor extends Collidable {
    private boolean alive;

    public Actor(Vector2 position, float width, float height) {
        center = position;
        size.set(width, height);
        color = Colors.randomColor();
        // set object into motion;
        velocity = new Vector2(0, 0);
        shouldTravel = true;
        alive = true;
    }

    public void setColor(Color color) {
        this.color = color;
        // position.TextureTintColor = color;
    }

    public Color getColor() {
        return color;
    }

    public void setTexture(String resourceName) {
        throw new UnsupportedOperationException();
        // position.texture = resourceName;
    }

    public void setLabel(String label) {
        throw new UnsupportedOperationException();
        // position.Label = label;
    }

    @Override
    public boolean isActive() {
        return alive;
    }

    public void kill() {
        alive = false;
    }

    @Override
    public void handleCollision(Collidable other) {
        if (other instanceof Obstacle) {
            collideWith(this, (Obstacle) other);
        }
    }

    private static void collideWith(Actor actor, Obstacle obstacle) {
        // Check collisions with each character and push them out of the
        // Collidable. This causes the player and enemy units to glide along the
        // edges of the Collidable
        Vector2 out = new Vector2(0, 0);
        if (Collision.isOverlap(actor, obstacle, out)) {
            // Move the character so that it's no longer overlapping the
            // debris
            actor.center.add(out);

            // Stop the character from moving if they collide with the
            // Collidable
            if (Math.abs(out.getX()) > 0.01f) {
                if (Math.signum(out.getX()) != Math.signum(actor.velocity
                        .getX())) {
                    actor.velocity.setX(0f);
                }
            }

            if (Math.abs(out.getY()) > 0.01f) {
                if (Math.signum(out.getY()) != Math.signum(actor.velocity
                        .getY())) {
                    actor.velocity.setY(0f);
                }
            }
        }
    }
}

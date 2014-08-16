package dyehard.Obstacles;

import java.util.HashSet;
import java.util.Set;

import Engine.Vector2;
import Engine.World.BoundCollidedStatus;
import dyehard.Actor;
import dyehard.Player.Hero;
import dyehard.Util.Collision;

/**
 * ObstacleManager provides a single class to handle collisions between actors
 * and obstacles.
 * 
 * Registering an Obstacle with ObstacleManager indicates that it is ready to
 * start colliding with registered Actors and vice versa.
 * 
 * Calling ObstacleManager.update() will call update on the registered obstacles
 * and then perform the collision checks handling the necessary interactions
 * between actors and obstacles.
 * 
 * @author Rodelle Ladia Jr.
 * 
 */
public class ObstacleManager {
    static Set<Actor> actors = new HashSet<Actor>();
    static Set<Obstacle> obstacles = new HashSet<Obstacle>();

    /**
     * Indicates that Actors are ready to start colliding with registered
     * obstacles. By default, collisions between Actors and Obstacles are
     * perfectly inelastic and frictionless.
     */
    public static void registerActor(Actor a) {
        if (a != null) {
            actors.add(a);
        }
    }

    /**
     * Indicates that obstacles are ready to start colliding with registered
     * Actors.
     * 
     * Registered obstacles are updated with every call to
     * ObstacleManager.update() and will be destroyed if they leave the game
     * world.
     */
    public static void registerObstacle(Obstacle o) {
        if (o != null) {
            obstacles.add(o);
        }
    }

    /**
     * Calls the update function for registered obstacles then handles
     * collisions between obstacles and actors.
     * 
     * Obstacles that move off the game world are destroyed.
     * 
     * By default, obstacles act as immovable objects. Actors colliding with an
     * obstacle are moved to prevent overlap and have their velocity reduced to
     * 0 to prevent further movement into the obstacle.
     */
    public static void update() {
        Set<Obstacle> destroyed = new HashSet<Obstacle>();
        for (Obstacle o : obstacles) {
            o.update();
            if (!isInsideWorld(o)) {
                o.destroy();
                destroyed.add(o);
            }
        }

        obstacles.removeAll(destroyed);

        for (Obstacle obstacle : obstacles) {
            for (Actor actor : actors) {
                handleCollision(obstacle, actor);
            }
        }
    }

    private static boolean isInsideWorld(Obstacle o) {
        // The obstacle is destroyed once it leaves the map through the left,
        // top, or bottom portion of the map.
        BoundCollidedStatus collisionStatus = o.collideWorldBound();
        if (!o.isInsideWorldBound()
                && collisionStatus != BoundCollidedStatus.RIGHT) {
            return false;
        }

        return true;
    }

    private static void handleCollision(Obstacle obstacle, Actor actor) {
        // Check collisions with each character and push them out of the
        // obstacle. This causes the player and enemy units to glide along the
        // edges of the obstacle
        Vector2 out = new Vector2(0, 0);

        if (actor instanceof Hero && ((Hero) actor).isGhost == true) {
            actor.removeFromAutoDrawSet();
            actor.addToAutoDrawSet();
            actor.draw();
            return;
        }

        if (Collision.isOverlap(actor, obstacle, out)) {
            // Move the character so that it's no longer overlapping the
            // debris
            actor.center.add(out);

            // Stop the character from moving if they collide with the
            // obstacle
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

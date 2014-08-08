package dyehard;

import java.util.HashSet;
import java.util.Set;

/**
 * Collision Manager provides a single class to handle collisions between
 * objects.
 * 
 * Registering an Collidable with CollidableManager indicates that it is ready
 * to start colliding with registered Actors and vice versa.
 * 
 * Calling CollidableManager.update() will call update on the registered
 * Collidables and then perform the collision checks handling the necessary
 * interactions between actors and Collidables.
 * 
 * @author Rodelle Ladia Jr.
 * 
 */
public class CollisionManager {
    static Set<Collidable> collidables = new HashSet<Collidable>();

    /**
     * Indicates that Actors are ready to start colliding with registered
     * Collidables. By default, collisions between Actors and Collidables are
     * perfectly inelastic and frictionless.
     */
    public static void registerCollidable(Collidable c) {
        if (c != null) {
            collidables.add(c);
        }
    }

    /**
     * Calls the update function for registered Collidables then handles
     * collisions between Collidables and actors.
     * 
     * Collidables that move off the game world are destroyed.
     * 
     * By default, Collidables act as immovable objects. Actors colliding with
     * an Collidable are moved to prevent overlap and have their velocity
     * reduced to 0 to prevent further movement into the Collidable.
     */
    public static void update() {
        for (Collidable a : collidables) {
            for (Collidable b : collidables) {
                if (a != b && a.collided(b)) {
                    a.handleCollision(b);
                    b.handleCollision(a);
                }
            }
        }

        Set<Collidable> destroyed = new HashSet<Collidable>();
        for (Collidable o : collidables) {
            if (o.isAlive == false) {
                o.destroy();
                destroyed.add(o);
            }
        }

        collidables.removeAll(destroyed);
    }
}

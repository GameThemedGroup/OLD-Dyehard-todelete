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
    static Set<Collidable> newCollidables = new HashSet<Collidable>();

    static Set<Collidable> actors = new HashSet<Collidable>();
    static Set<Collidable> newActors = new HashSet<Collidable>();

    /**
     * Indicates that Actors are ready to start colliding with other registered
     * actors and collidables.
     */
    public static void registerActor(Collidable c) {
        if (c != null) {
            newActors.add(c);
        }
    }

    /**
     * Registers an object that can collide with registered actors. Registered
     * collidables do not collide with other registered collidables.
     */
    public static void registerCollidable(Collidable c) {
        if (c != null) {
            newCollidables.add(c);
        }
    }

    public static void update() {
        for (Collidable actor : actors) {
            for (Collidable obj : collidables) {
                if (actor != obj && actor.collided(obj)) {
                    actor.handleCollision(obj);
                    obj.handleCollision(actor);
                }
            }
        }

        for (Collidable a : actors) {
            for (Collidable b : actors) {
                if (a != b && a.collided(b)) {
                    a.handleCollision(b);
                    b.handleCollision(a);
                }
            }
        }

        collidables.addAll(newCollidables);
        collidables.addAll(newActors);
        newCollidables.clear();
        removeInactiveObjects(collidables);

        actors.addAll(newActors);
        newActors.clear();
        removeInactiveObjects(actors);
    }

    private static void removeInactiveObjects(Set<Collidable> set) {
        Set<Collidable> destroyed = new HashSet<Collidable>();
        for (Collidable o : set) {
            if (o.isColliding() == false) {
                destroyed.add(o);
            }
        }

        set.removeAll(destroyed);
    }

    public static final Set<Collidable> getCollidables() {
        return collidables;
    }

    public static final Set<Collidable> getActors() {
        return actors;
    }
}

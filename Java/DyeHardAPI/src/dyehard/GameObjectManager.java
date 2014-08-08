package dyehard;

import java.util.HashSet;
import java.util.Set;

import Engine.World.BoundCollidedStatus;

public class GameObjectManager {
    static Set<GameObject> gameObjects = new HashSet<GameObject>();

    public static void update() {
        for (GameObject o : gameObjects) {
            if (o.isAlive) {
                o.update();
                o.removeFromAutoDrawSet();
                o.addToAutoDrawSet();
            }
        }

        Set<GameObject> destroyed = new HashSet<GameObject>();
        for (GameObject o : gameObjects) {
            if (o.isAlive == false || !isInsideWorld(o)) {
                o.destroy();
                destroyed.add(o);
            }
        }

        gameObjects.removeAll(destroyed);
    }

    public static void registerGameObject(GameObject o) {
        if (o != null) {
            gameObjects.add(o);
        }
    }

    private static boolean isInsideWorld(GameObject o) {
        // The Collidable is destroyed once it leaves the map through the left,
        // top, or bottom portion of the map.
        BoundCollidedStatus collisionStatus = o.collideWorldBound();
        if (!o.isInsideWorldBound()
                && collisionStatus != BoundCollidedStatus.RIGHT) {
            return false;
        }

        return true;
    }
}

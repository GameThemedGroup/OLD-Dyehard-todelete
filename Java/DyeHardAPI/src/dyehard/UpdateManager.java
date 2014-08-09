package dyehard;

import java.util.HashSet;
import java.util.Set;

public class UpdateManager {
    static Set<Updateable> gameObjects = new HashSet<Updateable>();
    static Set<Updateable> newlyRegisteredObjects = new HashSet<Updateable>();

    public static void update() {
        for (Updateable o : gameObjects) {
            if (o != null && o.isActive()) {
                o.update();
            }
        }

        gameObjects.addAll(newlyRegisteredObjects);
        newlyRegisteredObjects.clear();

        Set<Updateable> destroyed = new HashSet<Updateable>();
        for (Updateable o : gameObjects) {
            if (o == null || o.isActive() == false) {
                destroyed.add(o);
            }
        }

        gameObjects.removeAll(destroyed);
    }

    public static void register(Updateable o) {
        if (o != null) {
            newlyRegisteredObjects.add(o);
        }
    }
}
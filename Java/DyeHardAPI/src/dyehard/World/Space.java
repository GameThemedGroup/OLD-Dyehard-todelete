package dyehard.World;

import java.util.List;

import Engine.Vector2;
import dyehard.GameObject;
import dyehard.Collectibles.Collectible;
import dyehard.Obstacles.Debris;

public class Space extends GameWorldRegion {
    public static float WIDTH = GameWorld.RIGHT_EDGE * 3f;

    private List<Collectible> collectibles;
    private List<Debris> debris;
    private boolean isInitialized = false;

    public Space(float leftEdge) {
        width = WIDTH;
        position = leftEdge + width * 0.5f;
        speed = -GameWorld.Speed;
    }

    public void initialize(List<Collectible> collectibles, List<Debris> debris) {
        this.collectibles = collectibles;
        for (GameObject o : this.collectibles) {
            o.velocity = new Vector2(-GameWorld.Speed, 0f);
        }

        this.debris = debris;
        for (GameObject o : this.debris) {
            o.velocity = new Vector2(-GameWorld.Speed, 0f);
        }

        isInitialized = true;
    }

    public boolean isInitialized() {
        return isInitialized;
    }
}

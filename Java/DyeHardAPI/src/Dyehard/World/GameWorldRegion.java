package dyehard.World;

public abstract class GameWorldRegion {
    protected float width;
    protected float position;
    protected float speed;

    public float leftEdge() {
        return position - width / 2;
    }

    public float rightEdge() {
        return position + width / 2;
    }

    public float getWidth() {
        return width;
    }

    // Instructs the region to construct its components using the leftEdge as
    // its starting location
    public abstract void initialize(float leftEdge);

    // Update all of the primitives in the set
    public abstract void update();

    // Destroy all of the primitives in the set
    public abstract void destroy();
}

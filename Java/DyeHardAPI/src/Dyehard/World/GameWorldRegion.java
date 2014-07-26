package Dyehard.World;

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

    public abstract void update();

    public abstract void destroy();
}

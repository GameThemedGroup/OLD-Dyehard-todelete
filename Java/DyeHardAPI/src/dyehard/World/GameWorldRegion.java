package dyehard.World;

import dyehard.UpdateObject;

public abstract class GameWorldRegion extends UpdateObject {
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

    @Override
    public void update() {
        position += speed;
    }

    @Override
    public boolean isActive() {
        return rightEdge() > GameWorld.LEFT_EDGE;
    }
}

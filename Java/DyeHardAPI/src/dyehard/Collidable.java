package dyehard;

import dyehard.World.GameWorld;

public abstract class Collidable extends GameObject {
    protected ManagerState collidableState;

    private static final float offset = 30f;

    public Collidable() {
        collidableState = ManagerState.ACTIVE;
        CollisionManager.registerCollidable(this);
    }

    public ManagerState collideState() {
        return collidableState;
    }

    public abstract void handleCollision(Collidable other);

    @Override
    public void update() {
        super.update();
        if (!isInsideWorld(this)) {
            destroy();
        }
    }

    private static boolean isInsideWorld(Collidable o) {
        // The Collidable is destroyed once it's too far from the map to the
        // left, top, or bottom portion of the map. offset is 30f for now
        if ((o.center.getX() < (GameWorld.LEFT_EDGE - offset))
                || (o.center.getY() < (GameWorld.BOTTOM_EDGE - offset))
                || (o.center.getY() > (GameWorld.TOP_EDGE + offset))) {
            return false;
        }

        return true;
    }

    @Override
    public void destroy() {
        super.destroy();
        collidableState = ManagerState.DESTROYED;
    }
}

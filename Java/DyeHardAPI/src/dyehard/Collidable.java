package dyehard;

import Engine.World.BoundCollidedStatus;

public abstract class Collidable extends GameObject {
    protected ManagerState collidableState;

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
        // The Collidable is destroyed once it leaves the map through the left,
        // top, or bottom portion of the map.
        BoundCollidedStatus collisionStatus = o.collideWorldBound();
        if (!o.isInsideWorldBound()
                && collisionStatus != BoundCollidedStatus.RIGHT) {
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

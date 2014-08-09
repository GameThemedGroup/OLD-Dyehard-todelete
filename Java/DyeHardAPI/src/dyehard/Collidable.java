package dyehard;

public abstract class Collidable extends GameObject {
    public Collidable() {
        CollisionManager.registerCollidable(this);
    }

    public abstract void handleCollision(Collidable other);
}

package dyehard;

public class Collidable extends GameObject {
    public Collidable() {
        CollisionManager.registerCollidable(this);
    }

    public void handleCollision(Collidable other) {
        return;
    }
}

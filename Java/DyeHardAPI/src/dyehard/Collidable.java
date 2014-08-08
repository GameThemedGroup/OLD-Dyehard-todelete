package dyehard;

import Engine.Rectangle;

public class Collidable extends Rectangle {
    protected boolean isActive;

    public Collidable() {
        CollisionManager.registerCollidable(this);
    }

    protected void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public void handleCollision(Collidable other) {
        return;
    }
}

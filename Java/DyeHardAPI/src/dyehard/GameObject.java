package dyehard;

import Engine.Rectangle;
import dyehard.World.GameWorld;

public class GameObject extends Rectangle implements Updateable {
    protected boolean isActive = true;

    public GameObject() {
        GameWorld.registerUpdateable(this);
    }

    @Override
    public boolean isActive() {
        return isActive;
    }

    @Override
    public void destroy() {
        super.destroy();
        isActive = false;
    }

    @Override
    public void update() {
        super.update();
    }
}
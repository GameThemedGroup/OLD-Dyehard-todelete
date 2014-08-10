package dyehard;

import Engine.Rectangle;
import dyehard.UpdateManager.Updateable;

public class GameObject extends Rectangle implements Updateable {
    protected boolean isAlive = true;

    public GameObject() {
        UpdateManager.register(this);
    }

    @Override
    public boolean isActive() {
        return isAlive;
    }

    @Override
    public void destroy() {
        super.destroy();
        isAlive = false;
    }
}

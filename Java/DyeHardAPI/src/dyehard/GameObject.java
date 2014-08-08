package dyehard;

import Engine.Rectangle;

public class GameObject extends Rectangle {
    protected boolean isAlive = true;

    public GameObject() {
        GameObjectManager.registerGameObject(this);
    }

    public boolean isAlive() {
        return isAlive;
    }

    @Override
    public void destroy() {
        super.destroy();
        isAlive = false;
    }
}

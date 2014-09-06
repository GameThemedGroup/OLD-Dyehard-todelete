package dyehard;

import dyehard.UpdateManager.Updateable;

public class GameObject extends DyehardRectangle implements Updateable {
    protected boolean isUpdating = true;

    public GameObject() {
        UpdateManager.register(this);
    }

    @Override
    public boolean isActive() {
        return isUpdating;
    }

    @Override
    public void destroy() {
        super.destroy();
        isUpdating = false;
    }
}

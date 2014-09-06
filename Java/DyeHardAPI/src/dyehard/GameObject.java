package dyehard;

import dyehard.UpdateManager.Updateable;

public class GameObject extends DyehardRectangle implements Updateable {

    protected ManagerState updateState;

    public GameObject() {
        updateState = ManagerState.ACTIVE;
        UpdateManager.register(this);
    }

    @Override
    public ManagerState updateState() {
        return updateState;
    }

    @Override
    public void destroy() {
        super.destroy();
        updateState = ManagerState.DESTROYED;
    }
}

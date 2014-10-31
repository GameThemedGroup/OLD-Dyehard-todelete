package dyehard;

import UpdateManager;
import UpdateManager.Updateable;

public abstract class UpdateObject implements Updateable {

    public UpdateObject() {
        UpdateManager.register(this);
    }

    @Override
    public ManagerState updateState() {
        return ManagerState.ACTIVE;
    }

    @Override
    public abstract void update();
}
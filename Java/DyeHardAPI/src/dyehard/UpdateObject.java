package dyehard;

import dyehard.UpdateManager.Updateable;

public abstract class UpdateObject implements Updateable {

    public UpdateObject() {
        UpdateManager.register(this);
    }

    @Override
    public abstract boolean isActive();

    @Override
    public abstract void update();
}
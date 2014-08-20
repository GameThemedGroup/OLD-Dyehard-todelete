package dyehard;

import dyehard.World.GameWorld;

public abstract class UpdateObject implements Updateable {

    public UpdateObject() {
        GameWorld.registerUpdateable(this);
    }

    @Override
    public abstract boolean isActive();

    @Override
    public abstract void update();
}

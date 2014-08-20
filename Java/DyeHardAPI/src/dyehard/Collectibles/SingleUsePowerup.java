package dyehard.Collectibles;

import dyehard.Player.Hero;

public abstract class SingleUsePowerup extends PowerUp {

    public SingleUsePowerup(Hero hero, float minX, float maxX) {
        super(hero, minX, maxX);
    }

    protected boolean isApplied = false;

    @Override
    public void apply() {
        if (isApplied) {
            return;
        }

        applyOnce();
        isApplied = true;
    }

    public abstract void applyOnce();

    @Override
    public void unapply() {
        return;
    }

}

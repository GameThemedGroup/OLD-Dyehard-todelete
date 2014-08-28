package dyehard.Collectibles;

import dyehard.DHR;
import dyehard.Player.Hero;

public class Gravity extends PowerUp {

	protected float duration = DHR.getPowerupData(DHR.PowerupID.PU_GRAVITY).duration;
	protected float magnitude = DHR.getPowerupData(DHR.PowerupID.PU_GRAVITY).magnitude;
	
    public Gravity() {
        applicationOrder = 2;
        label.setText("Gravity");
    }

    @Override
    public void apply(Hero hero) {
        hero.currentGravity.set(0f, -magnitude);
    }

    @Override
    public void unapply(Hero hero) {
        hero.currentGravity.set(0f, 0f);
    }

    @Override
    public PowerUp clone() {
        return new Gravity();
    }

    @Override
    public String toString() {
        return "Gravity: " + super.toString();
    }

}

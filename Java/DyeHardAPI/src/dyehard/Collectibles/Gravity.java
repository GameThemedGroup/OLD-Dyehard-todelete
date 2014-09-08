package dyehard.Collectibles;

import dyehard.Configuration;
import dyehard.Player.Hero;

public class Gravity extends PowerUp {

    protected float magnitude = Configuration
            .getPowerUpData(Configuration.PowerUpType.PU_GRAVITY).magnitude;

    public Gravity() {
        duration = Configuration
                .getPowerUpData(Configuration.PowerUpType.PU_GRAVITY).duration * 1000;
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

package dyehard.Weapons;

import dyehard.Configuration;
import dyehard.Player.Hero;

public class OverHeatWeapon extends Weapon {
    private final float heatLimit = Configuration.overheatHeatLimit;
    private final float cooldownRate = Configuration.overheatCooldownRate;
    public float currentHeatLevel;
    public boolean overheated;

    public OverHeatWeapon(Hero hero) {
        super(hero);
        setFireRate(Configuration.overheatFiringRate);
        overheated = false;
        currentHeatLevel = 0;
    }

    @Override
    public void update() {
        if (currentHeatLevel > heatLimit) {
            overheated = true;
        }
        if (currentHeatLevel >= 0) {
            currentHeatLevel = currentHeatLevel - cooldownRate;
        }
        if (currentHeatLevel <= 0) {
            currentHeatLevel = 0;
            overheated = false;
        }
    }

    @Override
    public void fire() {
        if (timer.isDone() && !overheated) {
            super.fire();
            currentHeatLevel += 1;
        }
    }

    @Override
    public String toString() {
        return "Overheat " + String.format("%.0f", currentHeatLevel) + " / "
                + String.format("%.0f", heatLimit);
    }
}

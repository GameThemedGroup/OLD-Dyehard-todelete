package dyehard.Weapons;

import dyehard.Player.Hero;

public class OverHeatWeapon extends Weapon {
    private final float heatLimit = 10.0f;
    private final float cooldownRate = 0.05f;
    private float currentHeatLevel;
    private boolean overheated;

    public OverHeatWeapon(Hero hero) {
        super(hero);
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
        super.update();
    }

    @Override
    public void fire() {
        if (timer.isDone() && !overheated && !hero.isUnarmed) {
            super.fire();
            if (!hero.isOverloaded) {
                currentHeatLevel += 1;
            }
        }
    }

    public float getHeatLevel() {
        return currentHeatLevel;
    }

    @Override
    public String toString() {
        return "Overheat " + String.format("%.0f", currentHeatLevel) + " / "
                + String.format("%.0f", heatLimit);
    }
}

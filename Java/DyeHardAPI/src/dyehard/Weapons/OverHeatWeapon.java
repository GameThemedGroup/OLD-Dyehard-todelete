package dyehard.Weapons;

import java.awt.Color;

import Engine.Rectangle;
import Engine.Vector2;
import dyehard.Player.Hero;
import dyehard.World.GameWorld;

public class OverHeatWeapon extends Weapon {
    private final float heatLimit = 10.0f;
    private final float cooldownRate = 0.05f;
    private float currentHeatLevel;
    private boolean overheated;
    private Rectangle tempTracker;

    public OverHeatWeapon(Hero hero) {
        super(hero);
        overheated = false;
        currentHeatLevel = 0;
        tempTracker = new Rectangle();
        tempTracker.center = new Vector2(GameWorld.LEFT_EDGE + 4,
                GameWorld.TOP_EDGE - 4);
        tempTracker.size.set(4, 4);
    }

    @Override
    public void update() {
        if (currentHeatLevel > heatLimit) {
            overheated = true;
            tempTracker.color = Color.RED;
        }
        if (currentHeatLevel >= 0) {
            currentHeatLevel = currentHeatLevel - cooldownRate;
        }
        if (currentHeatLevel <= 0) {
            tempTracker.color = Color.GREEN;
            overheated = false;
        }
        super.update();
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
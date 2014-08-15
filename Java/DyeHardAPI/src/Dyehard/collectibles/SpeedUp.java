package dyehard.Collectibles;

import java.util.ArrayList;
import java.util.List;

import Engine.BaseCode;
import dyehard.Enemies.Enemy;
import dyehard.Enemies.EnemyManager;
import dyehard.Player.Hero;

public class SpeedUp extends PowerUp {
    // public static PowerUpMeter meter = new PowerUpMeter(0, DyeHard.Green);
    public SpeedUp() {
        texture = BaseCode.resources.loadImage("Textures/PowerUp_Green.png");
        enemySpeedModifier = 1.75f;
        isApplied = false;
        applicationOrder = 20;
    }

    protected boolean isApplied;
    protected final float enemySpeedModifier;
    protected List<Enemy> affectedEnemies;

    @Override
    public void apply(Hero hero) {
        if (isApplied) { // only applies once
            return;
        }

        affectedEnemies = new ArrayList<Enemy>(EnemyManager.getEnemies());

        for (Enemy e : affectedEnemies) {
            e.speed *= enemySpeedModifier;
        }

        isApplied = true;
    }

    @Override
    public void unapply(Hero hero) {
        for (Enemy e : affectedEnemies) {
            e.speed /= enemySpeedModifier;
        }
    }

    @Override
    public PowerUp clone() {
        return new SpeedUp();
    }

    @Override
    public String toString() {
        return super.toString() + " Speedup";
    }
}

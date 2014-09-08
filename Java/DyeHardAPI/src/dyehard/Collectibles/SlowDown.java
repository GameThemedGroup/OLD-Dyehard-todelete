package dyehard.Collectibles;

import java.util.ArrayList;
import java.util.List;

import Engine.BaseCode;
import dyehard.Configuration;
import dyehard.Enemies.Enemy;
import dyehard.Enemies.EnemyManager;
import dyehard.Player.Hero;

public class SlowDown extends PowerUp {

    protected float magnitude = Configuration
            .getPowerUpData(Configuration.PowerUpType.PU_SLOWDOWN).magnitude;

    public SlowDown() {
        super();
        duration = Configuration
                .getPowerUpData(Configuration.PowerUpType.PU_SLOWDOWN).duration * 1000;
        texture = BaseCode.resources.loadImage("Textures/PowerUp_Green.png");
        enemySpeedModifier = magnitude;
        isApplied = false;
        applicationOrder = 10;
        label.setText("Slow");
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
        return new SlowDown();
    }

    @Override
    public String toString() {
        return "Slow Down: " + super.toString();
    }

}

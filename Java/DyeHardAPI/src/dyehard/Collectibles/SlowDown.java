package dyehard.Collectibles;

import java.awt.Color;
import java.util.List;

import dyehard.Enemies.Enemy;
import dyehard.Player.Hero;

public class SlowDown extends SingleUsePowerup {
    List<Enemy> enemies;
    protected final float enemySpeedModifier = 0.5f;

    public SlowDown(Hero hero, List<Enemy> enemies) {
        super(hero);

        color = Color.gray;
        label.setText("Slow");

        usageOrder = 10;
    }

    @Override
    public void unapply() {
        for (Enemy e : enemies) {
            if (e != null) {
                e.chaseSpeed /= enemySpeedModifier;
            }
        }
    }

    @Override
    public void applyOnce() {
        for (Enemy e : enemies) {
            if (e != null) {
                e.chaseSpeed *= enemySpeedModifier;
            }
        }
    }

    @Override
    public String toString() {
        return super.toString() + " Slow Down";
    }
}

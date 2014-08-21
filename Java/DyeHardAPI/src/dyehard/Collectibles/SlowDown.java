package dyehard.Collectibles;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import dyehard.Enemies.Enemy;
import dyehard.Player.Hero;

public class SlowDown extends SingleUsePowerup {
    List<Enemy> affectedEnemies;
    protected final float enemySpeedModifier = 0.5f;

    public SlowDown(Hero hero, List<Enemy> enemies, float minX, float maxX) {
        super(hero, minX, maxX);
        affectedEnemies = new ArrayList<Enemy>(enemies);
        color = Color.gray;
        label.setText("Slow");

        usageOrder = 10;
    }

    @Override
    public void unapply() {
        for (Enemy e : affectedEnemies) {
            e.chaseSpeed /= enemySpeedModifier;
        }
    }

    @Override
    public void applyOnce() {
        for (Enemy e : affectedEnemies) {
            e.chaseSpeed *= enemySpeedModifier;
        }
    }

    @Override
    public String toString() {
        return "Slow Down: " + super.toString();
    }
}

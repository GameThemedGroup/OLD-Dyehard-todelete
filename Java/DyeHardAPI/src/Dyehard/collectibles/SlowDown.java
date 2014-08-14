package dyehard.Collectibles;

import java.awt.Color;
import java.util.List;

import dyehard.Enemies.Enemy;
import dyehard.Enemies.EnemyManager;
import dyehard.Player.Hero;

public class SlowDown extends PowerUp {
    List<Enemy> enemies;

    public SlowDown(Hero hero, List<Enemy> enemies, float minX, float maxX) {
        super(hero, minX, maxX);
        this.enemies = enemies;
        color = Color.gray;
        label.setText("Slow");
    }

    @Override
    public void activate() {
        for (Enemy e : enemies) {
            e.normalizeSpeed();
            e.decreaseSpeed();
        }
        EnemyManager.enemySpeed = "50%";
        PowerUpManager.EnemySpeedTimer.reset();
        super.activate();
    }

    @Override
    public String toString() {
        return "Slow Down";
    }
}

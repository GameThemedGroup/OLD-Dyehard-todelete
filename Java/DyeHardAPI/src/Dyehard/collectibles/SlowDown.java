package dyehard.Collectibles;

import java.awt.Color;
import java.util.List;

import dyehard.Enemies.Enemy;
import dyehard.Player.Hero;

public class SlowDown extends PowerUp {
    List<Enemy> enemies;

    public SlowDown(Hero hero, List<Enemy> enemies, float minX, float maxX) {
        super(hero, minX, maxX);
        this.enemies = enemies;
        color = Color.gray;
        // texture = BaseCode.resources.loadImage("Textures/PowerUp_Green.png");
    }

    @Override
    public void activate() {
        for (Enemy e : enemies) {
            e.normalizeSpeed();
            e.decreaseSpeed();
        }
        PowerUpManager.EnemySpeedTimer.reset();
        super.activate();
        System.out.println("Picked up Slow Down");
    }
}

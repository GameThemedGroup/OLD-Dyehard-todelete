package dyehard.Collectibles;

import java.awt.Color;
import java.util.List;

import Engine.BaseCode;
import dyehard.Enemies.Enemy;
import dyehard.Enemies.EnemyManager;
import dyehard.Player.Hero;

public class SpeedUp extends PowerUp {
    List<Enemy> enemies;

    public SpeedUp(Hero hero, List<Enemy> enemies, float minX, float maxX) {
        super(hero, minX, maxX);
        this.enemies = enemies;
        label.setText("Speed");
        label.setFrontColor(Color.white);
        texture = BaseCode.resources.loadImage("Textures/PowerUp_Green.png");
    }

    @Override
    public void activate() {
        for (Enemy e : enemies) {
            e.normalizeSpeed();
            e.increaseSpeed();
        }
        EnemyManager.enemySpeed = "175%";
        PowerUpManager.EnemySpeedTimer.reset();
        super.activate();
    }

    @Override
    public String toString() {
        return "Speed Up";
    }
}

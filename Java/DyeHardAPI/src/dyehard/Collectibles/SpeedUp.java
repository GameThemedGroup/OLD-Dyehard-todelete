package dyehard.Collectibles;

import java.awt.Color;
import java.util.List;

import Engine.BaseCode;
import dyehard.Enemies.Enemy;
import dyehard.Player.Hero;

public class SpeedUp extends SingleUsePowerup {
    List<Enemy> enemies;
    protected final float enemySpeedModifier = 1.5f;

    public SpeedUp(Hero hero, List<Enemy> enemies) {
        super(hero);
        this.enemies = enemies;
        label.setText("Speed");
        label.setFrontColor(Color.white);
        texture = BaseCode.resources.loadImage("Textures/PowerUp_Green.png");

        usageOrder = 20;
    }

    @Override
    public void unapply() {
        for (Enemy e : enemies) {
            if (e != null) {
                e.chaseSpeed *= enemySpeedModifier;
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
        return "Speed Up: " + super.toString();
    }
}

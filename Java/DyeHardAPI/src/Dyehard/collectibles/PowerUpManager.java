package dyehard.Collectibles;

import java.util.List;

import dyehard.Enemies.Enemy;
import dyehard.Player.Hero;
import dyehard.Util.Timer;

public class PowerUpManager {
    public static Timer EnemySpeedTimer;
    public static Timer GhostTimer;
    public static Timer OverloadTimer;
    public static Timer InvincibilityTimer;
    public static Timer UnarmedTimer;
    public static Timer MagnetismTimer;
    private Hero hero;
    private List<Enemy> enemies;

    public PowerUpManager(Hero hero, List<Enemy> enemies) {
        EnemySpeedTimer = new Timer(PowerUp.DURATION);
        GhostTimer = new Timer(PowerUp.DURATION);
        OverloadTimer = new Timer(PowerUp.DURATION);
        InvincibilityTimer = new Timer(PowerUp.DURATION);
        UnarmedTimer = new Timer(PowerUp.DURATION);
        MagnetismTimer = new Timer(PowerUp.DURATION);
        this.hero = hero;
        this.enemies = enemies;
    }

    public void update() {
        if (EnemySpeedTimer.isDone()) {
            for (Enemy e : enemies) {
                e.normalizeSpeed();
            }
        }
        if (GhostTimer.isDone()) {
            hero.ghostOff();
        }
        if (InvincibilityTimer.isDone()) {
            hero.invincibilityOff();
        }
        if (OverloadTimer.isDone()) {
            hero.overloadOff();
        }
        if (UnarmedTimer.isDone()) {
            hero.unarmedOff();
        }
        if (MagnetismTimer.isDone()) {
            hero.magnetismOff();
        }
    }
}

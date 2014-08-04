package dyehard.Collectibles;

import dyehard.Player.Hero;
import dyehard.Util.Timer;

public class PowerUpManager {
    public static Timer SpeedUpTimer;
    public static Timer GhostTimer;
    public static Timer OverloadTimer;
    public static Timer InvincibilityTimer;
    private Hero hero;

    public PowerUpManager(Hero hero) {
        SpeedUpTimer = new Timer(PowerUp.DURATION);
        GhostTimer = new Timer(PowerUp.DURATION);
        OverloadTimer = new Timer(PowerUp.DURATION);
        InvincibilityTimer = new Timer(PowerUp.DURATION);
        this.hero = hero;
    }

    public void update() {
        if (SpeedUpTimer.isDone()) {
            hero.normalizeSpeed();
        }
        if (GhostTimer.isDone()) {
            hero.ghostOff();
        }
    }
}

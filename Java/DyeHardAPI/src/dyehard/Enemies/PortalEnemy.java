package dyehard.Enemies;

import Engine.Vector2;
import dyehard.Configuration;
import dyehard.Configuration.EnemyType;
import dyehard.Player.Hero;
import dyehard.Util.Timer;

public class PortalEnemy extends Enemy {
    protected Timer timer;

    public PortalEnemy(Vector2 center, Hero currentHero) {
        super(center, 0, 0, currentHero, "Textures/Enemies/minion_portal.png");

        timer = new Timer(2000f);

        width = Configuration.getEnemyData(EnemyType.EN_PORTAL).width;
        height = Configuration.getEnemyData(EnemyType.EN_PORTAL).height;
        sleepTimer = Configuration.getEnemyData(EnemyType.EN_PORTAL).sleepTimer * 1000f;
        speed = Configuration.getEnemyData(EnemyType.EN_PORTAL).speed;
    }

    @Override
    public void update() {
        super.update();
        if (timer.isDone()) {
            new Portal(center.clone(), 4f, hero);
            timer.reset();
        }
    }

    @Override
    public String toString() {
        return "Portal";
    }
}
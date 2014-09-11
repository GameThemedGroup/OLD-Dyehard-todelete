package dyehard.Enemies;

import Engine.Vector2;
import dyehard.Configuration;
import dyehard.Configuration.EnemyType;
import dyehard.Player.Hero;
import dyehard.Util.Timer;

public class PortalEnemy extends Enemy {
    protected Timer timer;
    protected float portalSpawnInterval;

    public PortalEnemy(Vector2 center, Hero currentHero) {
        super(center, 0, 0, currentHero, "Textures/Enemies/minion_portal.png");

        portalSpawnInterval = Float.parseFloat(parseNodeList(
                EnemyType.PORTAL_ENEMY, "portalSpawnInterval")) * 1000;

        timer = new Timer(portalSpawnInterval);

        width = Configuration.getEnemyData(EnemyType.PORTAL_ENEMY).width;
        height = Configuration.getEnemyData(EnemyType.PORTAL_ENEMY).height;
        sleepTimer = Configuration.getEnemyData(EnemyType.PORTAL_ENEMY).sleepTimer * 1000f;
        speed = Configuration.getEnemyData(EnemyType.PORTAL_ENEMY).speed;
    }

    @Override
    public void update() {
        super.update();
        if (timer.isDone()) {
            new Portal(center.clone(), hero);
            timer.reset();
        }
    }

    @Override
    public String toString() {
        return "Portal";
    }
}
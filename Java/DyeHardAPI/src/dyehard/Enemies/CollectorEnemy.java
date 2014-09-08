package dyehard.Enemies;

import Engine.Vector2;
import dyehard.Configuration;
import dyehard.Configuration.EnemyType;
import dyehard.Player.Hero;

public class CollectorEnemy extends Enemy {

    public CollectorEnemy(Vector2 center, Hero currentHero) {
        super(center, 0, 0, currentHero,
                "Textures/Enemies/minion_collector.png");

        width = Configuration.getEnemyData(EnemyType.EN_COLLECTOR).width;
        height = Configuration.getEnemyData(EnemyType.EN_COLLECTOR).height;
        sleepTimer = Configuration.getEnemyData(EnemyType.EN_COLLECTOR).sleepTimer * 1000f;
        speed = Configuration.getEnemyData(EnemyType.EN_COLLECTOR).speed;
    }

    @Override
    public String toString() {
        return "Collector";
    }
}
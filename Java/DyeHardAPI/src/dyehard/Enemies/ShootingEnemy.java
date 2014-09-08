package dyehard.Enemies;

import Engine.Vector2;
import dyehard.Configuration;
import dyehard.Configuration.EnemyType;
import dyehard.Player.Hero;

public class ShootingEnemy extends Enemy {

    public ShootingEnemy(Vector2 center, Hero currentHero) {
        super(center, 0, 0, currentHero, "Textures/Enemies/minion_shooter.png");

        width = Configuration.getEnemyData(EnemyType.EN_SHOOTING).width;
        height = Configuration.getEnemyData(EnemyType.EN_SHOOTING).height;
        sleepTimer = Configuration.getEnemyData(EnemyType.EN_SHOOTING).sleepTimer * 1000f;
        speed = Configuration.getEnemyData(EnemyType.EN_SHOOTING).speed;
    }

    @Override
    public String toString() {
        return "Shooting";
    }
}
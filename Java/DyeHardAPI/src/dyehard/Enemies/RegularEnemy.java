package dyehard.Enemies;

import Engine.BaseCode;
import Engine.Vector2;
import dyehard.Configuration;
import dyehard.Configuration.EnemyType;
import dyehard.Player.Hero;

public class RegularEnemy extends Enemy {
    private boolean left;

    public RegularEnemy(Vector2 center, Hero currentHero) {
        super(center, 0, 0, currentHero,
                "Textures/Enemies/Regular_AnimSheet_Left.png");
        setUsingSpriteSheet(true);
        setSpriteSheet(texture, 212, 170, 5, 5);
        left = false;

        width = Configuration.getEnemyData(EnemyType.SHOOTING_ENEMY).width;
        height = Configuration.getEnemyData(EnemyType.SHOOTING_ENEMY).height;
        sleepTimer = Configuration.getEnemyData(EnemyType.SHOOTING_ENEMY).sleepTimer * 1000f;
        speed = Configuration.getEnemyData(EnemyType.SHOOTING_ENEMY).speed;
    }

    @Override
    public void update() {
        super.update();
        if ((velocity.getX() > 0) && left) {
            texture = BaseCode.resources
                    .loadImage("Textures/Enemies/Regular_AnimSheet_Right.png");
            setSpriteSheet(texture, 212, 170, 5, 5);
            left = false;
        } else if ((velocity.getX() < 0) && !left) {
            texture = BaseCode.resources
                    .loadImage("Textures/Enemies/Regular_AnimSheet_Left.png");
            setSpriteSheet(texture, 212, 170, 5, 5);
            left = true;
        }
    }

    @Override
    public String toString() {
        return "Shooting";
    }
}
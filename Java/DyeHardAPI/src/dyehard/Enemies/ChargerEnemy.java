package dyehard.Enemies;

import java.awt.Color;
import java.awt.image.BufferedImage;

import Engine.BaseCode;
import Engine.Vector2;
import dyehard.Configuration;
import dyehard.Configuration.EnemyType;
import dyehard.Player.Hero;
import dyehard.Util.Timer;
import dyehard.World.GameWorld;

public class ChargerEnemy extends Enemy {
    private boolean chasing;
    private boolean setImage;
    private boolean charge;
    private final float xOffset;
    private final float yOffset;
    private final Timer t;

    public ChargerEnemy(Vector2 center, Hero currentHero) {
        super(center, 0, 0, currentHero,
                "Textures/Enemies/Charger_AnimSheet_Idle.png");

        width = Configuration.getEnemyData(EnemyType.CHARGER_ENEMY).width;
        height = Configuration.getEnemyData(EnemyType.CHARGER_ENEMY).height;
        sleepTimer = Configuration.getEnemyData(EnemyType.CHARGER_ENEMY).sleepTimer * 1000f;
        speed = Configuration.getEnemyData(EnemyType.CHARGER_ENEMY).speed;

        chasing = false;
        setImage = false;
        charge = false;
        xOffset = 30f;
        yOffset = 5f;

        setUsingSpriteSheet(true);
        setSpriteSheet(texture, 340, 140, 13, 2);

        t = new Timer();
    }

    @Override
    public void update() {
        float deltaT = t.deltaTime();
        if (chasing) {
            if (!setImage) {
                BufferedImage temp = BaseCode.resources
                        .loadImage("Textures/Enemies/Charger_AnimSheet_Attack.png");
                setSpriteSheet(temp, 340, 140, 11, 8);
                setImage = true;
            }
            if ((getCurFrame() == 9) && (!charge)) {
                charge = true;
            }
            if (charge) {
                center = center.clone().add(
                        new Vector2(-speed * (deltaT * 500), 0f));
            }
        } else {
            center = center.clone().add(
                    new Vector2(-speed * (deltaT * 100), 0f));
            float xDif = Math.abs(hero.center.getX() - center.getX());
            float yDif = center.getY() - hero.center.getY();
            if ((xDif < xOffset) && (yDif < yOffset)) {
                chasing = true;
            }
        }

        if (center.getX() < GameWorld.LEFT_EDGE) {
            destroy();
        }
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Charger";
    }
}
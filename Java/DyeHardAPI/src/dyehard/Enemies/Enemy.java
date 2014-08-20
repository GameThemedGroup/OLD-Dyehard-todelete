package dyehard.Enemies;

import java.awt.Color;
import java.awt.image.BufferedImage;

import Engine.BaseCode;
import Engine.Vector2;
import dyehard.Actor;
import dyehard.Player.Hero;
import dyehard.Util.ImageTint;
import dyehard.Util.Timer;
import dyehard.World.GameWorld;

public class Enemy extends Actor {
    protected enum EnemyState {
        BEGIN, CHASEHERO, PLAYING, DEAD
    };

    protected Hero hero;
    protected EnemyState enemyState;
    protected BufferedImage baseTexture;

    private float behaviorChangeTime = 3000f;
    private Timer timer;

    public float floatSpeed;
    public float chaseSpeed;

    protected Enemy(Vector2 center, float width, float height,
            Hero currentHero, String texturePath) {
        super(center, width, height);
        hero = currentHero;
        baseTexture = BaseCode.resources.loadImage(texturePath);
        texture = baseTexture;
        enemyState = EnemyState.BEGIN;
        timer = new Timer(behaviorChangeTime);

        floatSpeed = Math.abs(GameWorld.Speed);
        chaseSpeed = floatSpeed * 1.3f;
    }

    @Override
    public void update() {
        switch (enemyState) {
        case BEGIN:
            drift();
            break;
        case CHASEHERO:
            chaseHero();
            break;
        case DEAD:
            destroy();
            break;
        default:
            break;
        }

        if (!alive) {
            enemyState = EnemyState.DEAD;
        }

        if (collided(hero)) {
            hero.kill();
        }

        super.update();
    }

    private void drift() {
        velocity = new Vector2(-floatSpeed, 0f);
        if (timer.isDone()) {
            enemyState = EnemyState.CHASEHERO;
        }
    }

    public void chaseHero() {
        Vector2 direction = new Vector2(hero.center).sub(center);
        direction.normalize();

        velocity.set(direction.mult(chaseSpeed));
    }

    public void gotShot(Color color) {
        setColor(color);
        texture = ImageTint.tintedImage(baseTexture, color, 0.25f);
    }
}
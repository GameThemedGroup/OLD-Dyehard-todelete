package BaseTypes;

import java.awt.Color;

import Dyehard.DyeHard;
import Dyehard.Player.Hero;
import Dyehard.World.GameWorld;
import Engine.Vector2;

public class Enemy extends Actor {
    protected enum EnemyState {
        BEGIN, CHASEHERO, PLAYING, DEAD
    };

    protected Hero hero;
    protected EnemyState enemyState;
    private float behaviorChangeTime = 5f;
    private long startTime;

    public Enemy(Vector2 center, float width, float height, Hero hero) {
        super(center, width, height);
        this.hero = hero;
        setColor(DyeHard.randomColor());
        enemyState = EnemyState.BEGIN;
        startTime = System.nanoTime();
    }

    @Override
    public void update() {
        if (System.nanoTime() >= startTime + (behaviorChangeTime * 1000000000)) {
            enemyState = EnemyState.CHASEHERO;
        }
        switch (enemyState) {
        case BEGIN:
            moveLeft();
            break;
        case CHASEHERO:
            chaseHero();
            break;
        default:
            break;
        }
        if (collided(hero)) {
            hero.kill();
        }
        super.update();
    }

    public void chaseHero() {
        if (GameWorld.Speed != 0) {
            Vector2 direction = new Vector2(hero.center.getX() - center.getX(),
                    hero.center.getY() - center.getY());
            direction.normalize();
            velocity = direction.mult(0.15f);
        } else {
            velocity = new Vector2(0, 0);
        }
    }

    public void moveLeft() {
        center.sub(new Vector2(GameWorld.Speed, 0));
    }

    public void gotShot(Color color) {
        setColor(color);
    }
}
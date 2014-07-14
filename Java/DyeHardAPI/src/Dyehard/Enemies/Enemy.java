package Dyehard.Enemies;

import java.awt.Color;

import Dyehard.Character;
import Dyehard.DyeHard;
import Dyehard.Player.Hero;
import Dyehard.World.GameWorld;
import Engine.Vector2;

public class Enemy extends Character {
    protected enum EnemyState {
        BEGIN, CHASEHERO, PLAYING, DEAD
    };

    protected Hero hero;
    protected EnemyState enemyState;

    public Enemy(Vector2 center, float width, float height, Hero hero) {
        super(center, width, height);
        this.hero = hero;
        setColor(DyeHard.randomColor());
        enemyState = EnemyState.BEGIN;
        // timer = new Timer(behaviorChangeTime);
    }

    @Override
    public void update() {
        // timer.update();
        // if (timer.isDone())
        // {
        // enemyState = EnemyState.CHASEHERO;
        // }
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
        if (getPosition().collided(hero.getPosition())) {
            hero.kill();
        }
        update();
    }

    public void chaseHero() {
        if (GameWorld.Speed != 0) {
            Vector2 direction = hero.getPosition().center.sub(position.center);
            direction.normalize();
            position.velocity = direction.mult(0.15f);
        } else {
            position.velocity = new Vector2(0, 0);
        }
    }

    public void moveLeft() {
        position.center.sub(new Vector2(-GameWorld.Speed, 0));
    }

    public void gotShot(Color color) {
        setColor(color);
    }
}
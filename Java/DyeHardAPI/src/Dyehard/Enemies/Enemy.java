package dyehard.Enemies;

import java.awt.Color;
import java.awt.image.BufferedImage;

import Engine.BaseCode;
import Engine.Vector2;
import dyehard.Actor;
import dyehard.Collidable;
import dyehard.Player.Hero;
import dyehard.Util.Colors;
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
    // This time is in milliseconds
    private float behaviorChangeTime = 5000f;
    private Timer timer;

    public Enemy(Vector2 center, float width, float height, Hero hero) {
        super(center, width, height);
        this.hero = hero;
        setColor(Colors.randomColor());
        enemyState = EnemyState.BEGIN;
        timer = new Timer(behaviorChangeTime);
    }

    protected Enemy(Vector2 center, float width, float height,
            Hero currentHero, String texturePath) {
        super(center, width, height);
        hero = currentHero;
        baseTexture = BaseCode.resources.loadImage(texturePath);
        texture = baseTexture;
        enemyState = EnemyState.BEGIN;
        timer = new Timer(behaviorChangeTime);
    }

    @Override
    public void update() {
        if (timer.isDone()) {
            enemyState = EnemyState.CHASEHERO;
            timer.reset();
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

    @Override
    public void handleCollision(Collidable other) {
        if (other instanceof Hero) {
            ((Hero) other).kill();
        }
    }

    @Override
    public void setColor(Color color) {
        super.setColor(color);
        texture = ImageTint.tintedImage(baseTexture, color, 0.25f);
    }
}
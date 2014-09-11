package dyehard.Enemies;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import Engine.BaseCode;
import Engine.Vector2;
import dyehard.Actor;
import dyehard.Collidable;
import dyehard.DyehardKeyboard;
import dyehard.Player.Hero;
import dyehard.Util.ImageTint;
import dyehard.Util.Timer;
import dyehard.World.GameWorld;

public class Enemy extends Actor {
    public float speed;

    protected enum EnemyState {
        BEGIN, CHASEHERO, PLAYING, DEAD
    };

    protected Hero hero;
    protected EnemyState enemyState;
    protected BufferedImage baseTexture;
    // This time is in milliseconds
    private float behaviorChangeTime = 3000f;
    private Timer timer;

    public Enemy(Vector2 center, float width, float height, Hero hero) {
        super(center, width, height);
        this.hero = hero;
        enemyState = EnemyState.BEGIN;
        timer = new Timer(behaviorChangeTime);
        speed = Math.abs(GameWorld.Speed);
    }

    protected Enemy(Vector2 center, float width, float height, Hero hero,
            String texturePath) {
        this(center, width, height, hero);
        baseTexture = BaseCode.resources.loadImage(texturePath);
        texture = baseTexture;
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
        if (DyehardKeyboard.isKeyTapped(KeyEvent.VK_I)) {
            center.add(new Vector2(0f, 3f));
        }

        if (DyehardKeyboard.isKeyTapped(KeyEvent.VK_J)) {
            center.add(new Vector2(-3f, 0f));
        }

        if (DyehardKeyboard.isKeyTapped(KeyEvent.VK_K)) {
            center.add(new Vector2(0f, -3f));
        }

        if (DyehardKeyboard.isKeyTapped(KeyEvent.VK_L)) {
            center.add(new Vector2(3f, 0f));
        }

        super.update();
    }

    public void chaseHero() {
        Vector2 direction = new Vector2(hero.center).sub(center);
        direction.normalize();

        if (direction.getX() > 0f) {
            // account for the relative movement of the game world
            direction.setX(direction.getX() * 2);
        }

        velocity.set(-speed, 0f);
        velocity.add(direction.mult(speed));
    }

    public void moveLeft() {
        velocity.setX(-speed);
    }

    @Override
    public void handleCollision(Collidable other) {
        super.handleCollision(other);
        if (other instanceof Hero && pixelTouches(hero)) {
            ((Hero) other).kill(this);
        }
    }

    @Override
    public void setColor(Color color) {
        super.setColor(color);
        texture = ImageTint.tintedImage(baseTexture, color, 0.25f);
    }
}
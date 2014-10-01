package dyehard.Enemies;

import java.awt.Color;
import java.awt.image.BufferedImage;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import Engine.BaseCode;
import Engine.Vector2;
import dyehard.Actor;
import dyehard.Collidable;
import dyehard.Configuration;
import dyehard.Configuration.EnemyType;
import dyehard.Player.Hero;
import dyehard.Util.ImageTint;
import dyehard.Util.Timer;

public class Enemy extends Actor {
    public float speed;
    public boolean beenHit = false;

    protected Hero hero;
    protected EnemyState enemyState;
    protected BufferedImage baseTexture;
    protected float width;
    protected float height;
    protected float sleepTimer;

    // This time is in milliseconds
    private Timer timer;

    protected enum EnemyState {
        BEGIN, CHASEHERO, PLAYING, DEAD
    };

    public Enemy(Vector2 center, float width, float height, Hero hero,
            String texturePath) {
        super(center, width, height);
        this.hero = hero;
        baseTexture = BaseCode.resources.loadImage(texturePath);
        texture = baseTexture;
    }

    public void initialize() {
        timer = new Timer(sleepTimer);
        size.set(width, height);
        enemyState = EnemyState.BEGIN;
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
        if ((other instanceof Hero) && (hero.damageOn)) {
            if (this instanceof ChargerEnemy) {
                ((Hero) other).kill(this);
            } else if (pixelTouches(hero)) {
                ((Hero) other).kill(this);
            }
        }
    }

    @Override
    public void setColor(Color color) {
        super.setColor(color);
        texture = ImageTint.tintedImage(baseTexture, color, 0.25f);
    }

    public String parseNodeList(EnemyType type, String tag) {
        NodeList nodeList = Configuration.getEnemyData(type).uniqueAttributes;
        String retVal = "";

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) node;

                retVal = elem.getElementsByTagName(tag).item(0).getChildNodes()
                        .item(0).getNodeValue();

                return retVal;
            }
        }

        return retVal;
    }
}
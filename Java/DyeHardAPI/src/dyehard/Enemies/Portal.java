package dyehard.Enemies;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.Random;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import Engine.BaseCode;
import Engine.Vector2;
import dyehard.Configuration;
import dyehard.Configuration.EnemyType;
import dyehard.GameObject;
import dyehard.Player.Hero;
import dyehard.Util.Timer;

public class Portal extends GameObject {
    protected Hero hero;
    protected Timer timer;
    protected float width;
    protected float height;
    protected float duration = 4000f;

    private final boolean collide;

    public Portal(Vector2 center, Hero hero) {
        this.center = center.clone();

        width = Configuration.getEnemyData(EnemyType.PORTAL_SPAWN).width;
        height = Configuration.getEnemyData(EnemyType.PORTAL_SPAWN).height;
        parseNodeList();

        size.set(width, height);
        this.hero = hero;
        texture = BaseCode.resources
                .loadImage("Textures/Enemies/Minion_Portal_AnimSheet.png");
        setUsingSpriteSheet(true);
        setSpriteSheet(texture, 160, 160, 20, 2);
        timer = new Timer(duration);
        collide = true;
    }

    public Portal(Hero hero) {
        collide = false;
        center = hero.center.clone();

        width = Configuration.getEnemyData(EnemyType.PORTAL_SPAWN).width;
        height = Configuration.getEnemyData(EnemyType.PORTAL_SPAWN).height;
        parseNodeList();

        size.set(width, height);
        this.hero = hero;
        texture = BaseCode.resources
                .loadImage("Textures/Enemies/Minion_Portal_AnimSheet.png");
        setUsingSpriteSheet(true);
        setSpriteSheet(texture, 160, 160, 20, 2);
        timer = new Timer(duration);
    }

    @Override
    public void update() {
        if (collide) {
            if (collided(hero)) {
                Random rand = new Random();
                hero.center.set(rand.nextInt(90) + 5, rand.nextInt(50) + 5);
                // move mouse to where center of hero is
                try {
                    Robot robot = new Robot();

                    robot.mouseMove((int) BaseCode.world
                            .worldToScreenX(hero.center.getX()),
                            (int) BaseCode.world.worldToScreenY(hero.center
                                    .getY()));
                } catch (AWTException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                hero.velocity = new Vector2(0f, 0f);
                destroy();
            }
        }
        if (timer.isDone()) {
            destroy();
        }
    }

    public void parseNodeList() {
        NodeList nodeList = Configuration.getEnemyData(EnemyType.PORTAL_SPAWN).uniqueAttributes;

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) node;

                duration = Float.parseFloat(elem
                        .getElementsByTagName("duration").item(0)
                        .getChildNodes().item(0).getNodeValue()) * 1000;
            }
        }
    }
}
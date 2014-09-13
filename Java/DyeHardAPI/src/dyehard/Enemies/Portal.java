package dyehard.Enemies;

import java.awt.Color;
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
    protected float duration;

    public Portal(Vector2 center, Hero hero) {
        this.center = center.clone();

        width = Configuration.getEnemyData(EnemyType.PORTAL_SPAWN).width;
        height = Configuration.getEnemyData(EnemyType.PORTAL_SPAWN).height;
        parseNodeList();

        size.set(width, height);
        this.hero = hero;
        texture = BaseCode.resources.loadImage("Textures/PowerUp_Box1.png");
        color = Color.black;
        timer = new Timer(duration);
    }

    @Override
    public void update() {
        if (collided(hero)) {
            Random rand = new Random();
            hero.center.set(rand.nextInt(90) + 5, rand.nextInt(50) + 5);
            hero.velocity = new Vector2(0f, 0f);
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
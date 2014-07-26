package Dyehard.Weapons;

import java.awt.Color;
import java.util.LinkedList;

import BaseTypes.Enemy;
import BaseTypes.Weapon;
import Dyehard.Player.Hero;
import Dyehard.World.GameWorld;
import Engine.Rectangle;
import Engine.Vector2;

public class SpreadFireWeapon extends Weapon {
    private Rectangle info;
    private LinkedList<Rectangle> angledBullets;

    public SpreadFireWeapon(Hero hero) {
        super(hero);
        angledBullets = new LinkedList<Rectangle>();
        info = new Rectangle();
        info.center = new Vector2(GameWorld.LEFT_EDGE + 12,
                GameWorld.TOP_EDGE - 4);
        info.size.set(4, 4);
        info.color = Color.BLUE;
    }

    @Override
    public void destroy() {
        info.removeFromAutoDrawSet();
        for (Rectangle b : angledBullets) {
            b.removeFromAutoDrawSet();
        }
        super.destroy();
    }

    @Override
    public void update() {
        // we have to maintain our own bullet queue because Weapon class will
        // otherwise
        // just move bullet horizontally
        for (int i = 0; i < angledBullets.size(); i++) {
            Rectangle b = angledBullets.get(i);
            b.center.offset(bulletSpeed, 0f);
            if (i % 2 == 0) {
                b.center.offset(0f, -(bulletSpeed / 2));
            } else {
                b.center.offset(0f, bulletSpeed / 2);
            }
        }
        while (angledBullets.size() > 0
                && (angledBullets.getFirst().center.getX() - (angledBullets
                        .getFirst().size.getX() / 2)) > GameWorld.RIGHT_EDGE) {
            angledBullets.removeFirst().removeFromAutoDrawSet();
        }
        for (Rectangle b : angledBullets) {
            for (Enemy e : enemies) {
                if (e.collided(b) && b.visible) {
                    e.gotShot(b.color);
                    b.visible = false;
                }
            }
        }
        super.update();
    }

    @Override
    public void fire() {
        if (currentTick >= lastTick + fireRate) {
            Rectangle bullet = new Rectangle();
            bullet.center = new Vector2(hero.center);
            bullet.size.set(bulletSize, bulletSize);
            bullet.color = hero.getColor();
            angledBullets.add(bullet);
            bullet = new Rectangle();
            bullet.center = new Vector2(hero.center);
            bullet.size.set(bulletSize, bulletSize);
            bullet.color = hero.getColor();
            angledBullets.add(bullet);
        }
        super.fire();
    }

    @Override
    public void draw() {
        info.draw();
        for (Rectangle b : angledBullets) {
            b.draw();
        }
        super.draw();
    }
}

package dyehard.Weapons;

import java.awt.Color;

import Engine.Vector2;
import dyehard.Collidable;
import dyehard.ManagerState;
import dyehard.Enemies.Enemy;
import dyehard.Player.Hero;

public class Bullet extends Collidable {
    public Color dyeColor;

    Hero hero;
    boolean firing = true;

    public Bullet(Hero hero) {
        this.hero = hero;

        size = hero.bulletSize;

        dyeColor = hero.getColor();
        texture = hero.bulletTextures.get(dyeColor);

        setUsingSpriteSheet(true);
        setSpriteSheet(texture, 590, 120, 7, 2);
    }

    @Override
    public void update() {
        // TODO replace with hero fire points
        if (hero.isInvin) {
            center = hero.center.clone().add(
                    new Vector2(size.getX() / 2, hero.size.getY() / 4.3f));
        } else {
            center = hero.center.clone().add(
                    new Vector2(size.getX() / 2, hero.size.getY() / 3.25f));
        }
        super.update();
        if (spriteCycleDone) {
            destroy();
        }
    }

    @Override
    public void handleCollision(Collidable other) {
        if ((other instanceof Enemy) && (other.color != dyeColor)) {
            if (pixelTouches(other)) {
                Enemy enemy = (Enemy) other;
                enemy.setColor(dyeColor);
                enemy.beenHit = true;
                collidableState = ManagerState.DESTROYED;
            }
        }
    }
}

package dyehard.Weapons;

import java.awt.Color;

import Engine.Vector2;
import dyehard.Collidable;
import dyehard.DyehardRectangle;
import dyehard.ManagerState;
import dyehard.Enemies.Enemy;
import dyehard.Player.Hero;

public class Bullet extends Collidable {
    public Color dyeColor;

    private final DyehardRectangle muzzle;

    Hero hero;
    boolean firing = true;

    public Bullet(Hero hero) {
        this.hero = hero;

        size = new Vector2(6.25f, 2.25f);
        dyeColor = hero.getColor();
        texture = hero.bulletTextures.get(dyeColor);
        center = hero.center.clone().add(
                new Vector2(size.getX() / 2, hero.size.getY() / 4f
                        + size.getY() * 0.5f));
        shouldTravel = true;
        velocity = new Vector2(3f, 0f);

        muzzle = new DyehardRectangle();
        muzzle.size = new Vector2(2.5f, 3.5f);
        muzzle.texture = hero.muzzleTextures.get(dyeColor);
        muzzle.setUsingSpriteSheet(true);
        muzzle.setSpriteSheet(muzzle.texture, 50, 70, 3, 3);

        hero.isFiring = true;
    }

    @Override
    public void update() {
        // TODO replace with hero fire points
        muzzle.center = hero.center.clone().add(
                new Vector2(size.getX() / 2, hero.size.getY() / 4f));

        if ((muzzle.spriteCycleDone) && (firing)) {
            System.out.println("done");
            hero.isFiring = false;
            muzzle.destroy();
            firing = false;
        } else if (!muzzle.spriteCycleDone) {
            hero.isFiring = true;
        }
        super.update();
    }

    @Override
    public void handleCollision(Collidable other) {
        if ((other instanceof Enemy) && (other.color != dyeColor)) {
            // int frame = getCurFrame();
            // float offset = 0;
            // if (frame == 3) {
            // offset = 0.15f;
            // } else if (frame == 4) {
            // offset = 0.3728f;
            // } else if (frame == 5) {
            // offset = 0.74f;
            // } else if (frame == 6) {
            // offset = 1f;
            // }
            //
            // float xMax = center.getX() + (offset * size.getX())
            // + (other.size.getX() * 0.5f);
            // float xMin = center.getX() - (size.getX() * 0.5f)
            // - (0.5f * other.size.getX());
            //
            // float yMax = center.getY() + (0.15f * size.getY())
            // + (other.size.getY() * 0.4f);
            // float yMin = center.getY() - (0.3f * size.getY())
            // - (other.size.getY() * 0.4f);
            //
            // if ((other.center.getX() < xMax) && (other.center.getX() > xMin)
            // && (other.center.getY() < yMax)
            // && (other.center.getY() > yMin)) {
            // Enemy enemy = (Enemy) other;
            // enemy.setColor(dyeColor);
            // enemy.beenHit = true;
            // collidableState = ManagerState.DESTROYED;
            // }
            Enemy enemy = (Enemy) other;
            enemy.setColor(dyeColor);
            enemy.beenHit = true;
            collidableState = ManagerState.DESTROYED;
        }
    }
}

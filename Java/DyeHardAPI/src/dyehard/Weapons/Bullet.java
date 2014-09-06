package dyehard.Weapons;

import java.awt.Color;

import Engine.Rectangle;
import Engine.Vector2;
import dyehard.Collidable;
import dyehard.DHR;
import dyehard.ManagerState;
import dyehard.Enemies.Enemy;
import dyehard.Player.Hero;
import dyehard.Util.ImageTint;
import dyehard.Util.Timer;

public class Bullet extends Collidable {
    public Color dyeColor;

    Timer timer;
    Hero hero;
    boolean firing = true;

    // TODO magic numbers
    static Rectangle r = DHR.getScaledRectangle(new Vector2(1920, 1080),
            new Vector2(590, 120),
            "Textures/dye_attack_muzzle_flash_animation.png");

    public Bullet(Hero hero) {
        this.hero = hero;
        timer = new Timer(500);

        size = r.size;

        dyeColor = hero.getColor();
        texture = ImageTint.tintedImage(r.texture, dyeColor, 1f);

        setUsingSpriteSheet(true);
        setSpriteSheet(texture, 590, 120, 7, 2);
    }

    @Override
    public void update() {
        // TODO replace with hero fire points
        center = hero.center.clone().add(new Vector2(size.getX() / 2, 0));
        super.update();

        if (timer.isDone()) {
            destroy();
        }
    }

    @Override
    public void handleCollision(Collidable other) {
        if (other instanceof Enemy && pixelTouches(other)) {
            Enemy enemy = (Enemy) other;
            enemy.setColor(dyeColor);
            collidableState = ManagerState.DESTROYED;
        }
    }
}

package dyehard.Obstacles;

import Engine.BaseCode;
import Engine.Vector2;
import dyehard.DyehardRectangle;
import dyehard.GameObject;
import dyehard.Player.Hero;

public class Laser extends GameObject {
    private final Hero hero;
    private final DyehardRectangle laserReverse;

    public Laser(Hero hero) {
        this.hero = hero;
        float height = BaseCode.world.getHeight()
                - BaseCode.world.getWorldPositionY();
        float width = height * 220 / 1024;
        center = new Vector2(width / 2 - 2, height / 2);
        size.set(width, height);
        texture = BaseCode.resources
                .loadImage("Textures/Background/DeathEdge.png");
        setPanning(true);
        setPanningSheet(texture, 16, 3, true);
        alwaysOnTop = true;

        laserReverse = new DyehardRectangle();
        laserReverse.size.set(width, height);
        laserReverse.center.set(width / 2 - 2, height / 2);
        laserReverse.setPanning(true);
        laserReverse.reverse = true;
        laserReverse.setPanningSheet(texture, 32, 3, true);
        laserReverse.alwaysOnTop = true;
    }

    // TODO should we put this collision into the user code?
    @Override
    public void update() {
        if (hero.center.getX() < center.getX()) {
            hero.kill(this);
        }
    }

    @Override
    public void destroy() {
        laserReverse.destroy();
        super.destroy();
    }
}
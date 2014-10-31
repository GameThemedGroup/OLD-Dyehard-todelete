package dyehard.Obstacles;

import Engine.BaseCode;
import Engine.Vector2;
import dyehard.GameObject;
import dyehard.Player.Hero;
import dyehard.Util.TextureTile;

public class Laser extends GameObject {
    private final Hero hero;

    public Laser(Hero hero) {
        this.hero = hero;
        float height = BaseCode.world.getHeight()
                - BaseCode.world.getWorldPositionY();
        float width = height * 220 / 1024;
        center = new Vector2(width / 2 - 2, height / 2);
        size.set(width, height);
        TextureTile tile = new TextureTile();
        texture = tile.setTiling(BaseCode.resources
                .loadImage("Textures/Background/DeathEdge.png"), 2, true);
        setPanning(true);
        setPanningSheet(texture, 220, 1024, 4, 4, true);
        alwaysOnTop = true;
    }

    // TODO should we put this collision into the user code?
    @Override
    public void update() {
        if (hero.center.getX() < center.getX()) {
            hero.kill(this);
        }
    }
}
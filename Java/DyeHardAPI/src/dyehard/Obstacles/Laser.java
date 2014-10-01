package dyehard.Obstacles;

import Engine.BaseCode;
import Engine.Vector2;
import dyehard.GameObject;
import dyehard.Player.Hero;
import dyehard.World.GameWorld;

public class Laser extends GameObject {
    private final Hero hero;

    public Laser(Hero hero) {
        this.hero = hero;
        float height = GameWorld.TOP_EDGE - GameWorld.BOTTOM_EDGE;
        float width = height * 220 / 512;
        center = new Vector2(width / 2 - 3, height / 2);
        size.set(width, height);
        texture = BaseCode.resources
                .loadImage("Textures/Background/DeathEdge.png");
        setPanning(true);
        setPanningSheet(texture, 220, 512, 20, 3, true);
    }

    // TODO should we put this collision into the user code?
    @Override
    public void update() {
        if (collided(hero)) {
            hero.kill(this);
        }
    }
}
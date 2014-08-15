package dyehard.Obstacles;

import java.awt.Color;

import Engine.Vector2;
import dyehard.GameObject;
import dyehard.Player.Hero;
import dyehard.World.GameWorld;

public class Laser extends GameObject {
    private Hero hero;

    public Laser(Hero hero) {
        this.hero = hero;
        float padding = 1f;
        float height = GameWorld.TOP_EDGE - GameWorld.BOTTOM_EDGE;
        float width = 1.5f;
        center = new Vector2(width / 2 + padding, height / 2);
        size.set(width, height);
        color = Color.lightGray;
    }

    // TODO should we put this collision into the user code?
    @Override
    public void update() {
        if (collided(hero)) {
            hero.kill();
        }
    }
}

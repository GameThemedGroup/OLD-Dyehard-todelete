package dyehard.Background;

import java.awt.Color;

import dyehard.World.GameWorld;


import Engine.Rectangle;
import Engine.Vector2;

public class Background {

    private Rectangle backdrop;
    private Starfield foreground;
    private Starfield background;
    private InfinityShip ship;

    public Background() {
        float width = GameWorld.RIGHT_EDGE - GameWorld.LEFT_EDGE;
        float height = GameWorld.TOP_EDGE - GameWorld.BOTTOM_EDGE;

        // Create a black background to represent space
        backdrop = new Rectangle();
        Vector2 center = new Vector2(width / 2, height / 2);
        backdrop.center = center;
        backdrop.size = new Vector2(width, height);
        backdrop.color = Color.BLACK;
        backdrop.shouldTravel = false;

        // TODO replace these magic numbers
        foreground = new Starfield(.8f, .2f, 30f);
        background = new Starfield(.4f, .1f, 10f);
        ship = new InfinityShip(0.04f);
    }

    public void update() {
        backdrop.update();
        background.update();
        foreground.update();
        ship.update();
    }

}

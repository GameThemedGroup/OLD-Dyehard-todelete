package dyehard.Background;

import java.awt.Color;

import Engine.BaseCode;
import Engine.Rectangle;
import Engine.Vector2;
import dyehard.World.GameWorld;

public class Background {

    private Rectangle backdrop;
    private Starfield foreground;
    private Starfield background;

    public Background() {
        float width = GameWorld.RIGHT_EDGE - GameWorld.LEFT_EDGE;
        float height = GameWorld.TOP_EDGE - GameWorld.BOTTOM_EDGE;

        // The infinity ship currently consists of 4 game tiles
        width *= 4;

        // Create a black background to represent space
        backdrop = new Rectangle();
        Vector2 center = new Vector2(width / 2, height / 2);

        backdrop.center = center;
        backdrop.texture = BaseCode.resources
                .loadImage("Textures/ship_background.png");
        backdrop.velocity = new Vector2(-0.04f, 0f);
        backdrop.size = new Vector2(width, height);
        backdrop.color = Color.BLACK;
        backdrop.shouldTravel = true;

        // TODO replace these magic numbers
        foreground = new Starfield(.8f, .2f, 30f);
        background = new Starfield(.4f, .1f, 10f);
    }

    public void update() {
        backdrop.update();
        background.update();
        foreground.update();
    }

}

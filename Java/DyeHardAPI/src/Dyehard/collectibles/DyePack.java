package dyehard.Collectibles;

import java.awt.Color;
import java.util.Random;

import Engine.BaseCode;
import Engine.Rectangle;
import Engine.Vector2;
import dyehard.Player.Hero;
import dyehard.Util.Colors;
import dyehard.World.GameWorld;

public class DyePack extends Rectangle {
    private static Random RANDOM = new Random();
    private final float height = 3.5f;
    private final float width = 3f;
    protected Hero hero;

    public DyePack(Hero hero, float minX, float maxX, Color color) {
        this.hero = hero;

        float randomX = (maxX - minX - width) * RANDOM.nextFloat() + minX
                + width / 2f;
        float randomY = (GameWorld.TOP_EDGE - GameWorld.BOTTOM_EDGE - height)
                * RANDOM.nextFloat() + height / 2f;

        center.set(new Vector2(randomX, randomY));
        size.set(width, height);
        velocity = new Vector2(-GameWorld.Speed, 0f);
        shouldTravel = true;
        this.color = color;

        texture = BaseCode.resources.loadImage("Textures/" + getTexture(color));
    }

    @Override
    public void update() {
        super.update();
        if (collided(hero) && visible) {
            hero.collect(this);
        }
    }

    public void activate() {
        hero.setColor(color);
        visible = false;
    }

    private static String getTexture(Color color) {
        if (color == Colors.Green) {
            return "Colors.png";
        }
        if (color == Colors.Blue) {
            return "Dye_Blue.png";
        }
        if (color == Colors.Yellow) {
            return "Dye_Yellow.png";
        }
        if (color == Colors.Teal) {
            return "Dye_Teal.png";
        }
        if (color == Colors.Pink) {
            return "Dye_Pink.png";
        }
        if (color == Colors.Red) {
            return "Dye_Red.png";
        }
        return "";
    }
}

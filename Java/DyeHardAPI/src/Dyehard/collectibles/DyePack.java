package dyehard.Collectibles;

import java.awt.Color;

import Engine.BaseCode;
import Engine.Rectangle;
import Engine.Vector2;
import dyehard.Player.Hero;
import dyehard.Util.Colors;

public class DyePack extends Rectangle {
    public static final float height = 3.5f;
    public static final float width = 3f;
    protected Hero hero;

    public DyePack(Hero hero, Color color) {
        this.hero = hero;
        this.color = color;
        texture = BaseCode.resources.loadImage("Textures/" + getTexture(color));
        shouldTravel = false;
    }

    public void initialize(Vector2 center, Vector2 velocity) {
        this.center = center;
        this.velocity = velocity;

        size.set(width, height);
        shouldTravel = true;
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
            return "Dye_Green.png";
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

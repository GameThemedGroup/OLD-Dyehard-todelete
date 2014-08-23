package dyehard.Collectibles;

import java.awt.Color;

import Engine.BaseCode;
import Engine.Vector2;
import dyehard.Collidable;
import dyehard.Player.Hero;
import dyehard.Util.Colors;

public class DyePack extends Collidable {
    public static final float height = 3.5f;
    public static final float width = 3f;

    public DyePack(Color color) {
        this.color = color;
        texture = BaseCode.resources.loadImage(getTexture(color));
        shouldTravel = false;
        visible = false;
    }

    public void initialize(Vector2 center, Vector2 velocity) {
        this.center = center;
        this.velocity = velocity;

        size.set(width, height);
        shouldTravel = true;
        visible = true;
    }

    public void activate(Hero hero) {
        hero.setColor(color);
        visible = false;
    }

    public static String getTexture(Color color) {
        if (color == Colors.Green) {
            return "Textures/Dye_Green.png";
        }
        if (color == Colors.Blue) {
            return "Textures/Dye_Blue.png";
        }
        if (color == Colors.Yellow) {
            return "Textures/Dye_Yellow.png";
        }
        if (color == Colors.Teal) {
            return "Textures/Dye_Teal.png";
        }
        if (color == Colors.Pink) {
            return "Textures/Dye_Pink.png";
        }
        if (color == Colors.Red) {
            return "Textures/Dye_Red.png";
        }
        return "";
    }

    @Override
    public void handleCollision(Collidable other) {
        if (other instanceof Hero) {
            Hero hero = (Hero) other;
            hero.collect(this);
        }
    }
}

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
        texture = BaseCode.resources.loadImage("Textures/" + getTexture(color));
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

    @Override
    public void handleCollision(Collidable other) {
        if (other instanceof Hero) {
            System.out.println("Collected dye pack");
            Hero hero = (Hero) other;
            hero.collect(this);
        }
    }
}

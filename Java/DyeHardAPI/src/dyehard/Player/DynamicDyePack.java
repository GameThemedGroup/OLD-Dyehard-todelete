package dyehard.Player;

import java.awt.Color;

import Engine.BaseCode;
import dyehard.GameObject;
import dyehard.Player.Hero.State;
import dyehard.Util.Colors;

public class DynamicDyePack extends GameObject {
    Hero hero;
    private final float height = 3.5f;
    private final float width = 3f;

    public DynamicDyePack(Hero hero) {
        this.hero = hero;
        size.set(width, height);
        texture = BaseCode.resources.loadImage("Textures/"
                + getTexture(hero.color));
    }

    @Override
    public void update() {
        super.update();
        updatePosition();
    }

    private void updatePosition() {
        if (hero.getState() == State.TOPLEFT) {
            rotate = 30f;
            center.set(hero.center.getX() - 4, hero.center.getY() + 4);
        } else if (hero.getState() == State.TOPRIGHT) {
            rotate = -30f;
            center.set(hero.center.getX() + 4, hero.center.getY() + 4);
        } else if (hero.getState() == State.BOTTOMLEFT) {
            rotate = 30f;
            center.set(hero.center.getX() - 4, hero.center.getY() - 4);
        } else if (hero.getState() == State.BOTTOMRIGHT) {
            rotate = -30f;
            center.set(hero.center.getX() + 4, hero.center.getY() - 4);
        } else if (hero.getState() == State.UP) {
            rotate = 0f;
            center.set(hero.center.getX(), hero.center.getY() + 4);
        } else if (hero.getState() == State.DOWN) {
            rotate = 0f;
            center.set(hero.center.getX(), hero.center.getY() - 4);
        } else if (hero.getState() == State.LEFT) {
            rotate = 0f;
            center.set(hero.center.getX() - 4, hero.center.getY());
        } else if (hero.getState() == State.RIGHT) {
            rotate = 0f;
            center.set(hero.center.getX() + 4, hero.center.getY());
        } else if (hero.getState() == State.NEUTRAL) {
            rotate = 0f;
            center.set(hero.center.getX(), hero.center.getY());
        }
    }

    public void updateColor(Color color) {
        texture = BaseCode.resources.loadImage("Textures/" + getTexture(color));
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

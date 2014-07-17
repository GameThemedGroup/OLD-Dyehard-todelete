package Dyehard.Powerups;

import java.awt.Color;
import java.util.Random;

import Dyehard.DyeHard;
import Dyehard.GameObject;
import Dyehard.Player.Hero;
import Dyehard.World.GameWorld;
import Engine.BaseCode;
import Engine.Rectangle;
import Engine.Vector2;

public class DyePack extends GameObject {
    private final float height = 3.5f;
    protected Hero hero;
    protected Rectangle box;

    public DyePack(Hero hero, float minX, float maxX, Color color) {
        this.hero = hero;
        float padding = hero.getPosition().size.getX() * 2;
        Random rand = new Random();
        float randomX = (maxX - padding - minX + padding) * rand.nextFloat()
                + minX + padding;
        // TODO: 480f and 0f are place holders for topEdge and bottomEdge
        float randomY = (480f - padding - 0f + padding) * rand.nextFloat() + 0f
                + padding;
        box = new Rectangle();
        box.center.set(new Vector2(randomX, randomY));
        box.size.set(0.865f * height, height);
        box.color = color;
        box.texture = BaseCode.resources.loadImage("Textures/"
                + getTexture(color));
    }

    @Override
    public void remove() {
        box.removeFromAutoDrawSet();
    }

    public void move() {
        box.center.setX(box.center.getX() - GameWorld.Speed);
    }

    @Override
    public void update() {
        if (box.collided(hero.getPosition()) && box.visible) {
            hero.collect(this);
        }
    }

    public void activate() {
        hero.setColor(box.color);
        box.visible = false;
    }

    @Override
    public void draw() {
        box.removeFromAutoDrawSet();
        box.addToAutoDrawSet();
    }

    private static String getTexture(Color color) {
        if (color == DyeHard.Green) {
            return "Dye_Green.png";
        }
        if (color == DyeHard.Blue) {
            return "Dye_Blue.png";
        }
        if (color == DyeHard.Yellow) {
            return "Dye_Yellow.png";
        }
        if (color == DyeHard.Teal) {
            return "Dye_Teal.png";
        }
        if (color == DyeHard.Pink) {
            return "Dye_Pink.png";
        }
        if (color == DyeHard.Red) {
            return "Dye_Red.png";
        }
        return "";
    }
}

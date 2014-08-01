package Dyehard;

import java.awt.Color;

import Dyehard.Util.Colors;
import Engine.Rectangle;
import Engine.Vector2;

public class Actor extends Rectangle {
    private boolean alive;

    public Actor(Vector2 position, float width, float height) {
        center = position;
        size.set(width, height);
        color = Colors.randomColor();
        // set object into motion;
        velocity = new Vector2(0, 0);
        shouldTravel = true;
        alive = true;
    }

    public void setColor(Color color) {
        this.color = color;
        // position.TextureTintColor = color;
    }

    public Color getColor() {
        return color;
    }

    public void setTexture(String resourceName) {
        throw new UnsupportedOperationException();
        // position.texture = resourceName;
    }

    public void setLabel(String label) {
        throw new UnsupportedOperationException();
        // position.Label = label;
    }

    public boolean isAlive() {
        return alive;
    }

    public void kill() {
        alive = false;
    }
}

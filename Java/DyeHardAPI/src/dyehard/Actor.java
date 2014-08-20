package dyehard;

import java.awt.Color;

import Engine.Vector2;
import dyehard.Util.Colors;

public class Actor extends GameObject {
    protected boolean alive;

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

    public boolean isAlive() {
        return alive;
    }

    public void kill() {
        alive = false;
    }
}

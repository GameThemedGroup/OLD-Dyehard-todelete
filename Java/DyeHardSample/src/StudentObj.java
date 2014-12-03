import java.awt.image.BufferedImage;

import Engine.BaseCode;
import Engine.Vector2;

public class StudentObj {
    private float height;
    private float width;
    private final Vector2 center;
    private BufferedImage texture;

    public StudentObj() {
        center = new Vector2(0, 0);
        height = 5f;
        width = 5f;
        texture = BaseCode.resources.loadImage("Beak.png");
    }

    public StudentObj(Vector2 c, float w, float h) {
        center = c;
        height = h;
        width = w;
        texture = BaseCode.resources.loadImage("Beak.png");
    }

    public void setCenter(float x, float y) {
        center.set(x, y);
    }

    public void setHeight(float h) {
        height = h;
    }

    public void setWidth(float w) {
        width = w;
    }

    public void setTexture(BufferedImage img) {
        texture = img;
    }

    public Vector2 getCenter() {
        return center;
    }

    public float getHeight() {
        return height;
    }

    public float getWidth() {
        return width;
    }

    public BufferedImage getTexture() {
        return texture;
    }
}

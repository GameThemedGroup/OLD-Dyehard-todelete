import Engine.Vector2;

public class StudentObj {
    private float height;
    private float width;
    private Vector2 center;

    public StudentObj() {
        center.set(new Vector2(0, 0));
        height = 5f;
        width = 5f;
    }

    public StudentObj(Vector2 c, float w, float h) {
        center = c;
        height = h;
        width = w;
    }

    public void setCenter(Vector2 c) {
        center.set(c.clone());
    }

    public void setHeight(float h) {
        height = h;
    }

    public void setWidth(float w) {
        width = w;
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
}

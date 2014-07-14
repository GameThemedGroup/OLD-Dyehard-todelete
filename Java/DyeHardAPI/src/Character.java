import java.awt.Color;
import java.util.ArrayList;

import Engine.Rectangle;
import Engine.Vector2;

public class Character extends GameObject {
    protected Rectangle position;
    private boolean alive;
    private Rectangle nextPosition;
    private ArrayList<Rectangle> pendingCollisions;

    public Character(Vector2 position, float width, float height) {
        this.position = new Rectangle();
        this.position.center.set(position);
        this.position.size.set(width, height);
        this.position.color = DyeHard.randomColor();
        nextPosition = new Rectangle();
        nextPosition.center.set(position);
        nextPosition.size.set(width, height);
        nextPosition.color = DyeHard.randomColor();
        nextPosition.visible = false;
        // set object into motion;
        this.position.velocity = new Vector2(0, 0);
        this.alive = true;
        // this.pendingCollisions = new ArrayList<Rectangle>();
    }

    @Override
    public void remove() {
        position.removeFromAutoDrawSet();
        nextPosition.removeFromAutoDrawSet();
    }

    @Override
    public void draw() {
        // .TopOfAutoDrawSet
        // There is a function in the library under the ResourceHandler
        // class that is called moveToFrontOfDrawSet that does this.
        // moveToFrontOfDrawSet has been commented out of the
        // Primitive class so this will have to do.
        position.removeFromAutoDrawSet();
        position.addToAutoDrawSet();
    }

    public void setColor(Color color) {
        position.color = color;
        // position.TextureTintColor = color;
    }

    public Color getColor() {
        return position.color;
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
        return this.alive;
    }

    public void kill() {
        alive = false;
    }

    public Rectangle getPosition() {
        return position;
    }

    public Rectangle getNextPosition() {
        nextPosition.center = position.center.add(position.velocity);
        return nextPosition;
    }

    @Override
    public void update() {
        interpretCollisions();
        position.center.add(position.velocity);
    }

    public void addCollision(Rectangle box) {
        pendingCollisions.add(box);
    }

    public void interpretCollisions() {
        for (Rectangle obstacle : pendingCollisions) {
            collide(getNextPosition(), obstacle);
        }
        // all collisions handled, remove all boxes
        pendingCollisions.clear();
    }

    private void collide(Rectangle pending, Rectangle obstacle) {
    }
}

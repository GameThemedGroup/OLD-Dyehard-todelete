import java.util.ArrayList;

import Engine.Rectangle;
import Engine.Vector2;

public class Character extends GameObject {
    private boolean alive;
    protected Rectangle position;
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
    public void draw() {
        // .TopOfAutoDrawSet
        // There is a function in the library under the ResourceHandler
        // class that is called moveToFrontOfDrawSet that does this.
        // moveToFrontOfDrawSet has been commented out of the
        // Primitive class so this will have to do.
        position.removeFromAutoDrawSet();
        position.addToAutoDrawSet();
    }

    public boolean isAlive() {
        return this.alive;
    }

    @Override
    public void remove() {
        position.removeFromAutoDrawSet();
        nextPosition.removeFromAutoDrawSet();
    }

    @Override
    public void update() {
        position.center.add(position.velocity);
    }

    public Rectangle getNextPosition() {
        nextPosition.center = position.center.add(position.velocity);
        return nextPosition;
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

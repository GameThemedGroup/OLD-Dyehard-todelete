import Engine.Vector2;

public class Hero extends Character {

    private final float horizontalSpeedLimit = 0.8f;
    private static float drag = 0.96f; // smaller number means more reduction
    // private final float rightBoundaryLimit = 0.85f; // percentage of screen

    // private int collectedDyepacks;
    // private int collectedPowerups;

    private float speedFactor;
    private boolean invisible;

    public Hero() {
        super(new Vector2(20f, 20f), 5f, 5f);

        // collectedDyepacks = 0;
        // collectedPowerups = 0;

        // initialize powerup variables
        speedFactor = 1.0f;
        invisible = false;
    }

    @Override
    public void draw() {
        super.draw();
    }

    public void push(Vector2 direction) {
        // scale direction
        direction.set(direction.getX() / 12f, direction.getY() / 12f);
        direction.mult(speedFactor);

        // add 'jetpack' factor
        if (direction.getY() > 0) {
            direction.setY(direction.getY() * 1.7f);
        }

        // update velocity
        position.velocity = (position.velocity.add(direction
                .add(GameWorld.Gravity))).mult(drag);

        if (position.velocity.getX() < 0) {
            position.velocity.setX(Math.max(position.velocity.getX(), -1
                    * horizontalSpeedLimit));
        } else {
            position.velocity.setX(Math.min(position.velocity.getX(),
                    horizontalSpeedLimit));
        }
    }

    @Override
    public void remove() {
        super.remove();
    }

    @Override
    public void update() {
        // restrict the hero's movement to the boundary
        boolean holdVisibility = invisible;
        invisible = false;

        // update base character object (collisions, etc.)
        super.update();
        invisible = holdVisibility;
    }

}

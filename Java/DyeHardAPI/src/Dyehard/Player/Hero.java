package Dyehard.Player;
import java.awt.event.KeyEvent;

import Dyehard.Character;
import Dyehard.World.GameWorld;
import Engine.KeyboardInput;
import Engine.Vector2;

public class Hero extends Character {
    private final float horizontalSpeedLimit = 0.8f;
    private static float drag = 0.96f; // smaller number means more reduction
    // private final float rightBoundaryLimit = 0.85f; // percentage of screen
    // private int collectedDyepacks;
    // private int collectedPowerups;
    private float speedFactor;
    private boolean invisible;
    private KeyboardInput keyboard;

    public Hero(KeyboardInput keyboard) {
        super(new Vector2(20f, 20f), 5f, 5f);
        // collectedDyepacks = 0;
        // collectedPowerups = 0;
        // initialize powerup variables
        speedFactor = 1.0f;
        invisible = false;
        this.keyboard = keyboard;
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
        handleInput();
        // restrict the hero's movement to the boundary
        boolean holdVisibility = invisible;
        invisible = false;
        // update base character object (collisions, etc.)
        super.update();
        invisible = holdVisibility;
    }

    private void handleInput() {
        // The C# version uses joystick input to control the hero
        // I found 0.75 by printing out the value of the vector
        // being returned by the joy stick control
        Vector2 inputDirection = new Vector2(0, 0);
        if (keyboard.isButtonDown(KeyEvent.VK_UP)) {
            inputDirection.add(new Vector2(0f, 0.75f));
        }
        if (keyboard.isButtonDown(KeyEvent.VK_LEFT)) {
            inputDirection.add(new Vector2(-0.75f, 0f));
        }
        if (keyboard.isButtonDown(KeyEvent.VK_DOWN)) {
            inputDirection.add(new Vector2(0f, -0.75f));
        }
        if (keyboard.isButtonDown(KeyEvent.VK_RIGHT)) {
            inputDirection.add(new Vector2(0.75f, 0f));
        }
        push(inputDirection);
    }
}

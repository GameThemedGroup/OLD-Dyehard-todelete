package Dyehard.Obstacles;

import java.awt.Color;
import java.util.List;

import Dyehard.Character;
import Engine.Rectangle;
import Engine.Vector2;

public class Obstacle extends Rectangle {
    List<Character> characters;

    public Obstacle(List<Character> characters, Vector2 center, Vector2 size,
            Vector2 velocity) {
        this.center = center;
        this.size = size;
        this.velocity = velocity;
        shouldTravel = true;
        color = Color.GREEN;
        this.characters = characters;
    }

    @Override
    public void update() {
        super.update();
        checkCollisions();
    }

    private void checkCollisions() {
        if (!isInsideWorldBound()) {
            destroy();
            return;
        }
        for (Character c : characters) {
            if (collided(c.getPosition())) {
                pushOutCircle(c.getPosition());
            }
        }
    }
}

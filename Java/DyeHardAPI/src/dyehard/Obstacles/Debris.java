package dyehard.Obstacles;

import java.util.Random;

import Engine.BaseCode;

public class Debris extends Obstacle {
    private static Random RANDOM = new Random();
    private final float height = 6f;
    private final float width = 6f;

    public Debris() {
        size.set(width, height);
        shouldTravel = true;

        initializeRandomTexture();
    }

    private void initializeRandomTexture() {
        switch (RANDOM.nextInt(3)) {
        case 0:
            texture = BaseCode.resources.loadImage("Textures/Beak.png");
            break;
        case 1:
            texture = BaseCode.resources.loadImage("Textures/Window.png");
            break;
        case 2:
            texture = BaseCode.resources.loadImage("Textures/Wing2.png");
            size.setX(size.getY() * 1.8f);
            break;
        }
    }
}

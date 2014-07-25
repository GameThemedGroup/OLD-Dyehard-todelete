package Dyehard.Obstacles;

import java.util.Random;

import Dyehard.World.GameWorld;
import Engine.BaseCode;
import Engine.Vector2;

public class Debris extends Obstacle {
    private static Random RANDOM = new Random();
    private final float height = 6f;
    private final float width = 6f;

    public Debris(float minX, float maxX) {
        float randomX = (maxX - minX - width) * RANDOM.nextFloat() + minX
                + width / 2f;
        float randomY = (GameWorld.TOP_EDGE - GameWorld.BOTTOM_EDGE - height)
                * RANDOM.nextFloat() + height / 2f;

        center.set(new Vector2(randomX, randomY));
        size.set(width, height);
        velocity = new Vector2(-GameWorld.Speed, 0f);
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

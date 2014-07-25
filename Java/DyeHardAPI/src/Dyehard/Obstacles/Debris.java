package Dyehard.Obstacles;

import java.util.Random;

import Dyehard.World.GameWorld;
import Engine.BaseCode;
import Engine.Vector2;

public class Debris extends Obstacle {
    private static Random RANDOM = new Random();
    protected static float height = 6f;

    public Debris(float minX, float maxX) {
        float padding = height;
        float randomX = (maxX - padding - minX + padding) * RANDOM.nextFloat()
                + minX + padding;
        float randomY = (GameWorld.TOP_EDGE - padding - GameWorld.BOTTOM_EDGE + padding)
                * RANDOM.nextFloat() + 0f + padding;
        center.set(new Vector2(randomX, randomY));
        size.set(height, height);
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

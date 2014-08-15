package dyehard.Background;

import java.awt.Color;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Random;

import Engine.Rectangle;
import Engine.Vector2;
import dyehard.GameObject;
import dyehard.World.GameWorld;

public class Starfield extends GameObject {
    private Queue<Rectangle> stars;
    private float speed;
    private float size;
    private static Random RANDOM = new Random();

    public Starfield(float size, float speed, float spacing) {
        this.size = size;
        this.speed = speed;
        stars = new ArrayDeque<Rectangle>();
        // fill background
        for (float xPos = 0; xPos < GameWorld.RIGHT_EDGE; xPos += spacing) {
            stars.add(createStarAt(xPos));
        }
    }

    public void update() {
        for (Rectangle star : stars) {
            star.update();
        }

        // recycle stars to right edge of screen
        if (stars.element().center.getX() <= GameWorld.LEFT_EDGE) {
            Rectangle star = stars.remove();
            star.center.setX(GameWorld.RIGHT_EDGE);
            star.center.setY(RANDOM.nextFloat() * GameWorld.TOP_EDGE);
            stars.add(star);
        }
    }

    private static Color randomStarColor() {
        int r = RANDOM.nextInt(56) + 200;
        int g = RANDOM.nextInt(56) + 200;
        int b = RANDOM.nextInt(56) + 200;

        return new Color(r, g, b);
    }

    private Rectangle createStarAt(float xPos) {
        Rectangle star = new Rectangle();

        float randomYPos = RANDOM.nextFloat() * GameWorld.TOP_EDGE;
        Vector2 position = new Vector2(xPos, randomYPos);

        star.center = position;
        star.size = new Vector2(size, size);
        star.velocity = new Vector2(-speed, 0f);
        star.shouldTravel = true;
        star.color = randomStarColor();

        return star;
    }
}

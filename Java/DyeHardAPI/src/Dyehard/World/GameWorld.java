package Dyehard.World;

import java.util.ArrayList;
import java.util.List;

import Dyehard.Character;
import Dyehard.DeveloperControls;
import Dyehard.Obstacles.Obstacle;
import Dyehard.Player.Hero;
import Engine.KeyboardInput;
import Engine.Primitive;
import Engine.Vector2;

public class GameWorld extends Primitive {
    // private final float StartSpeed = 0.2f;
    public static float Speed = .5f;
    public static Vector2 Gravity = new Vector2(0, -0.02f);
    private Hero hero;
    private DeveloperControls dev;
    private List<Primitive> primitives;
    private List<Character> characters;
    private static final int TimeToSpawnDebris = 40;
    int frameCount = 0;

    public GameWorld(KeyboardInput keyboard) {
        hero = new Hero(keyboard);
        dev = new DeveloperControls(keyboard, hero);
        characters = new ArrayList<Character>();
        characters.add(hero);
        primitives = new ArrayList<Primitive>();
    }

    // Increments the timer used to generate obstacles
    // If the timer's duration has been reached, an obstacle is generated and
    // added to the list of primitives
    private void generateObstacle() {
        frameCount++;
        if (frameCount < TimeToSpawnDebris) {
            return;
        }
        frameCount = 0;
        float startY = (float) (Math.random() * 80 + 5);
        Vector2 position = new Vector2(100, startY);
        Vector2 size = new Vector2(5, 5);
        Vector2 speed = new Vector2(-Speed, 0);
        primitives.add(new Obstacle(characters, position, size, speed));
    }

    @Override
    public void draw() {
        hero.draw();
    }

    public boolean gameOver() {
        return !hero.isAlive();
    }

    @Override
    public void destroy() {
        hero.destroy();
    }

    @Override
    public void update() {
        generateObstacle();
        hero.update();
        dev.update();
        for (Primitive p : primitives) {
            p.update();
        }
        for (int i = primitives.size() - 1; i >= 0; --i) {
            if (primitives.get(i).visible == false) {
                primitives.remove(i);
            }
        }
    }
}
